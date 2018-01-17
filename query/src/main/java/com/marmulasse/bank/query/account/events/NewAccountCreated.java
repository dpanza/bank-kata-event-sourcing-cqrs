package com.marmulasse.bank.query.account.events;

import com.marmulasse.bank.query.account.balance.AccountId;

public class NewAccountCreated implements AccountEvent {

    private AccountId accountId;

    public NewAccountCreated() {}

    public NewAccountCreated(AccountId accountId) {
        this.accountId = accountId;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewAccountCreated that = (NewAccountCreated) o;

        return accountId != null ? accountId.equals(that.accountId) : that.accountId == null;

    }

    @Override
    public int hashCode() {
        return accountId != null ? accountId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NewAccountCreated{" +
                "accountId=" + accountId +
                '}';
    }
}
