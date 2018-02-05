package com.marmulasse.bank.account.aggregate.events;

import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.AccountProjection;
import com.marmulasse.bank.account.aggregate.Balance;

import java.util.Objects;

public class NewAccountCreated implements AccountEvent<AccountProjection> {

    private AccountId accountId;
    private Balance balance;

    public NewAccountCreated(AccountId accountId, Balance balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public Balance getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewAccountCreated that = (NewAccountCreated) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, balance);
    }

    @Override
    public String toString() {
        return "NewAccountCreated{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }

    @Override
    public AccountProjection apply(AccountProjection projection) {
        return projection.stateApplier().apply(this);
    }
}
