package com.marmulasse.bank.account.aggregate;

import java.util.UUID;

public class AccountId {
    public UUID value;


    public static AccountId create(UUID uuid) {
        return new AccountId(uuid);
    }

    public static AccountId create() {
        return new AccountId(UUID.randomUUID());
    }

    public static AccountId from(String id){
        return new AccountId(UUID.fromString(id));
    }

    public AccountId() {
    }

    private AccountId(UUID uuid) {
        this.value = uuid;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountId accountId = (AccountId) o;

        return value != null ? value.equals(accountId.value) : accountId.value == null;

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AccountId{" +
                "value=" + value +
                '}';
    }
}
