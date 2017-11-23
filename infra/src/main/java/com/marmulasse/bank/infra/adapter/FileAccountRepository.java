package com.marmulasse.bank.infra.adapter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.events.AccountEvent;
import com.marmulasse.bank.account.port.AccountRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class FileAccountRepository implements AccountRepository {

    private final Path path;
    private final ObjectMapper objectMapper;

    public FileAccountRepository(String filepath) {
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
        account.getNewChanges()
                .forEach(event -> {
                    try {
                        String line = formatLine(event);
                        write(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void write(String line) throws IOException {
        Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
    }

    private String formatLine(AccountEvent event) throws JsonProcessingException {
        String eventLine = objectMapper.writeValueAsString(event);
        return String.format("%s;%s\n", event.getClass().getSimpleName(), eventLine);
    }
}
