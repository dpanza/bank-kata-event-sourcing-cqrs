package com.marmulasse.bank.account.events;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;

public class NewAccountCreated implements AccountEvent {

    private AccountId accountId;

    public NewAccountCreated() {}

    public NewAccountCreated(AccountId accountId) {
        this.accountId = accountId;
    }

    @Override
    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public String getName() {
        return "NewAccountCreated";
    }

    @Override
    public void apply(Account account) {
        account.apply(this);
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
