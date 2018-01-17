package com.marmulasse.bank.query.account.balance;

import java.util.UUID;

public class AccountId {

    private UUID value;

    public AccountId() {
    }

    private AccountId(UUID uuid) {
        value = uuid;
    }

    public static AccountId from(String id) {
        return new AccountId(UUID.fromString(id));
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
