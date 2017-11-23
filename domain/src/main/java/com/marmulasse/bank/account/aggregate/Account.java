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
    protected AccountId accountId;

    public static Account empty() {
        return new Account(AccountId.create());
    }

    public static Account with(AccountId accountId) {
        return new Account(accountId);
    }

    private Account(AccountId accountId) {
        NewAccountCreated newAccountCreated = new NewAccountCreated(accountId);
        apply(newAccountCreated);
        saveChange(newAccountCreated);
    }

    public void deposit(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A deposit must be positive");
        NewDepositMade newDepositMade = new NewDepositMade(accountId, amount);
        apply(newDepositMade);
        saveChange(newDepositMade);
    }

    private void apply(NewDepositMade newDepositMade) {
        this.balance = this.balance.add(newDepositMade.getAmount());
    }

    private void apply(NewAccountCreated newAccountCreated) {
        this.accountId = newAccountCreated.getAccountId();
        this.balance = Balance.ZERO;
    }

    private void saveChange(AccountEvent accountEvent) {
        this.newChanges.add(accountEvent);
    }

    public AccountId getAccountId() {
        return accountId;
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

        if (balance != null ? !balance.equals(account.balance) : account.balance != null) return false;
        if (newChanges != null ? !newChanges.equals(account.newChanges) : account.newChanges != null) return false;
        return accountId != null ? accountId.equals(account.accountId) : account.accountId == null;

    }

    @Override
    public int hashCode() {
        int result = balance != null ? balance.hashCode() : 0;
        result = 31 * result + (newChanges != null ? newChanges.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", newChanges=" + newChanges +
                ", accountId=" + accountId +
                '}';
    }

}
