package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.events.AccountEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EventDescriptor {
    private static final Pattern EVENT_DESCRIPTOR_PATTERN = Pattern.compile("(?<accountId>[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12});(?<timestamp>\\w+);(?<eventName>\\w+);(?<event>\\{.*:\\{.*:.*\\}\\})");
    private final String accountId;
    private final String eventName;
    private final long timestamp;
    private final String eventAsString;

    EventDescriptor(String accountId, String eventName, long timestamp, String eventAsString) {
        this.accountId = accountId;
        this.eventName = eventName;
        this.timestamp = timestamp;
        this.eventAsString = eventAsString;
    }

    public static EventDescriptor of(AccountEvent accountEvent, String contentAsString, long time) {
        String accountId = accountEvent.getAccountId().value.toString();
        String name = accountEvent.getName();
        return new EventDescriptor(accountId, name, time, contentAsString);
    }

    public static EventDescriptor rebuild(String line) {
        Matcher matcher = EVENT_DESCRIPTOR_PATTERN.matcher(line);
        matcher.find();
        return new EventDescriptor(matcher.group("accountId"), matcher.group("eventName"), Long.parseLong(matcher.group("timestamp")), matcher.group("event"));
    }

    public String getAccountId() {
        return accountId;
    }

    public String getEventAsString() {
        return eventAsString;
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventDescriptor that = (EventDescriptor) o;

        if (timestamp != that.timestamp) return false;
        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (eventName != null ? !eventName.equals(that.eventName) : that.eventName != null) return false;
        if (eventAsString != null ? !eventAsString.equals(that.eventAsString) : that.eventAsString != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (eventAsString != null ? eventAsString.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s\n", accountId, timestamp, eventName, eventAsString);
    }

    public void print(Path path) throws IOException {
        Files.write(path, this.toString().getBytes(), StandardOpenOption.APPEND);
    }
}
