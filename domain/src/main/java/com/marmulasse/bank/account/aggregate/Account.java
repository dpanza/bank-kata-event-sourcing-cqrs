package com.marmulasse.bank.account.aggregate;

import com.google.common.base.Preconditions;
import com.marmulasse.bank.account.events.AccountEvent;
import com.marmulasse.bank.account.events.NewAccountCreated;
import com.marmulasse.bank.account.events.NewDepositMade;

import java.util.ArrayList;
import java.util.List;

public class Account {
    protected Balance balance;
    protected List<AccountEvent> newChanges = new ArrayList<>();

    public static Account empty() {
        return new Account(Balance.ZERO);
    }

    private Account(Balance balance) {
        NewAccountCreated newAccountCreated = new NewAccountCreated(balance);
        apply(newAccountCreated);
        saveChange(newAccountCreated);
    }

    public void deposit(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A deposit must be positive");
        NewDepositMade newDepositMade = new NewDepositMade(amount);
        apply(newDepositMade);
        saveChange(newDepositMade);
    }

    private void apply(NewDepositMade newDepositMade) {
        this.balance = this.balance.add(newDepositMade.getAmount());
    }

    private void apply(NewAccountCreated newAccountCreated) {
        this.balance = newAccountCreated.getBalance();
    }

    private void saveChange(AccountEvent accountEvent) {
        this.newChanges.add(accountEvent);
    }

    public List<AccountEvent> getNewChanges() {
        return newChanges;
    }

    public Balance getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return balance != null ? balance.equals(account.balance) : account.balance == null;

    }

    @Override
    public int hashCode() {
        return balance != null ? balance.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                '}';
    }

}
