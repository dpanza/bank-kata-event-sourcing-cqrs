package com.marmulasse.bank.infra.adapter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.events.AccountEvent;
import com.marmulasse.bank.account.port.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class FileAccountRepository implements AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(FileAccountRepository.class);
    private final Clock clock;
    private final Path path;
    private final ObjectMapper objectMapper;

    public FileAccountRepository(String filepath, Clock clock) {
        this.clock = clock;
        path = Paths.get(filepath);
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public void save(Account account) {
        List<EventDescriptor> eventDescriptors = toEventDescriptors(account);
        eventDescriptors
                .forEach(eventDescriptor -> {
                    try {
                        eventDescriptor.print(path);
                    } catch (IOException e) {
                        throw new RuntimeException("Cannot print to file ", e);
                    }
                });
    }

    private List<EventDescriptor> toEventDescriptors(Account account) {
        List<EventDescriptor> eventDescriptors = account.getNewChanges()
                .stream()
                .map(toEventDescriptor())
                .collect(Collectors.toList());
        Preconditions.checkArgument(!eventDescriptors.contains(null), "Some event deserialization failed");
        return eventDescriptors;
    }

    private Function<AccountEvent, EventDescriptor> toEventDescriptor() {
        return accountEvent -> {
            try {
                return EventDescriptor.of(accountEvent, objectMapper.writeValueAsString(accountEvent), clock.millis());
            } catch (JsonProcessingException e) {
                log.error("Cannot serialize ", e);
            }
            return null;
        };
    }

    @Override
    public Optional<Account> get(AccountId accountId) {
        List<String> lines = readLines();
        List<AccountEvent> accountEvents = toAccountEvents(accountId, lines);
        return Account.rebuildFrom(accountEvents);

    }

    private List<AccountEvent> toAccountEvents(AccountId accountId, List<String> lines) {
        LinkedList<AccountEvent> accountEvents = lines.stream()
                .map(EventDescriptor::rebuild)
                .filter(eventDescriptor -> eventDescriptor.getAccountId().equals(accountId.value.toString()))
                .map(eventDescriptor -> toAccountEvent(eventDescriptor.getEventName(), eventDescriptor.getEventAsString()))
                .collect(Collectors.toCollection(LinkedList::new));
        Preconditions.checkArgument(!accountEvents.contains(null), "Some event deserialization failed");
        return accountEvents;
    }

    private AccountEvent toAccountEvent(String eventName, String eventAsString) {
        try {
            Class<?> eventClass = Class.forName("com.marmulasse.bank.account.events." + eventName);
            return (AccountEvent) new ObjectMapper().readValue(eventAsString, eventClass);
        } catch (ClassNotFoundException | IOException e) {
            log.error("Cannot deserialize event", e);
        }
        return null;
    }

    private List<String> readLines() {
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read rebuildFrom specified file in path : " + path);
        }
        return lines;
    }
}
