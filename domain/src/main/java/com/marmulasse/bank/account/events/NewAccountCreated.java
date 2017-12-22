package com.marmulasse.bank.account.events;

import com.marmulasse.bank.account.Balance;

public class NewAccountCreated implements AccountEvent {

    private Balance balance;

    public NewAccountCreated(Balance balance) {
        this.balance = balance;
    }

    public Balance getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewAccountCreated that = (NewAccountCreated) o;

        return balance != null ? balance.equals(that.balance) : that.balance == null;

    }

    @Override
    public int hashCode() {
        return balance != null ? balance.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NewAccountCreated{" +
                "balance=" + balance +
                '}';
    }
}
