package com.marmulasse.bank.account;

import com.google.common.base.Preconditions;
import com.marmulasse.bank.account.events.AccountEvent;
import com.marmulasse.bank.account.events.NewAccountCreated;
import com.marmulasse.bank.account.events.NewDepositMade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    private AccountId accountId;
    private Balance balance;
    protected List<AccountEvent> uncommittedChanges = new ArrayList<>();

    public static Account empty() {
        return new Account(AccountId.create(), Balance.ZERO);
    }

    public static Account with(Balance balance) {
        return new Account(AccountId.create(), balance);
    }

    private Account(AccountId accountId, Balance balance) {
        NewAccountCreated newAccountCreated = new NewAccountCreated(accountId, balance);
        apply(newAccountCreated);
        saveUncommittedChange(newAccountCreated);
    }

    public void deposit(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A deposit must be positive");
        NewDepositMade newDepositMade = new NewDepositMade(this.accountId, amount);
        apply(newDepositMade);
        saveUncommittedChange(newDepositMade);
    }

    private void apply(NewDepositMade newDepositMade) {
        this.balance = this.balance.add(newDepositMade.getAmount());
    }

    private void apply(NewAccountCreated newAccountCreated) {
        this.accountId = newAccountCreated.getAccountId();
        this.balance = newAccountCreated.getBalance();
    }

    private void saveUncommittedChange(AccountEvent accountEvent) {
        this.uncommittedChanges.add(accountEvent);
    }

    public Balance getBalance() {
        return balance;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }

    public List<AccountEvent> getUncommittedChanges() {
        return uncommittedChanges;
    }
}
