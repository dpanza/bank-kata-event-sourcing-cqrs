package com.marmulasse.bank.account.aggregate;

import com.google.common.base.Preconditions;
import com.marmulasse.bank.account.aggregate.events.AccountEvent;
import com.marmulasse.bank.account.aggregate.events.NewAccountCreated;
import com.marmulasse.bank.account.aggregate.events.NewDepositMade;
import com.marmulasse.bank.account.aggregate.events.NewWithdrawMade;

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

    public static Account rebuild(List<AccountEvent> events) {
        return events.stream()
                .reduce(new Account(),
                        (account, event) -> event.apply(account),
                        (account1, account2) -> account2);
    }

    private Account() {}

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

    public void withdraw(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A withdraw must be positive");
        NewWithdrawMade newWithdrawMade = new NewWithdrawMade(this.accountId, amount);
        apply(newWithdrawMade);
        saveUncommittedChange(newWithdrawMade);
    }

    public Account apply(NewDepositMade newDepositMade) {
        this.balance = this.balance.add(newDepositMade.getAmount());
        return this;
    }

    public Account apply(NewAccountCreated newAccountCreated) {
        this.accountId = newAccountCreated.getAccountId();
        this.balance = newAccountCreated.getBalance();
        return this;
    }

    public Account apply(NewWithdrawMade newWithdrawMade) {
        this.balance = this.balance.minus(newWithdrawMade.getAmount());
        return this;
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
