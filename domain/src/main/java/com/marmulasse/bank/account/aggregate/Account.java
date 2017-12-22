package com.marmulasse.bank.account.aggregate;

import com.google.common.base.Preconditions;
import com.marmulasse.bank.account.events.AccountEvent;
import com.marmulasse.bank.account.events.NewAccountCreated;
import com.marmulasse.bank.account.events.NewDepositMade;
import com.marmulasse.bank.account.events.NewWithdrawMade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Account {
    protected Amount balance;
    protected AccountId accountId;
    protected List<AccountEvent> newChanges = new ArrayList<>();

    public static Account empty() {
        return new Account(AccountId.create());
    }

    public static Optional<Account> rebuildFrom(List<AccountEvent> events) {
        if(events.isEmpty()) {
            return Optional.empty();
        }
        Account account = new Account();
        events.forEach(accountEvent -> accountEvent.apply(account));
        return Optional.of(account);
    }

    private Account() {
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

    public void withdraw(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A withdraw must be positive");

        NewWithdrawMade newWithdrawMade = new NewWithdrawMade(accountId, amount);
        apply(newWithdrawMade);
        saveChange(newWithdrawMade);
    }

    public void apply(NewDepositMade newDepositMade) {
        this.balance = this.balance.plus(newDepositMade.getAmount());
    }

    public void apply(NewAccountCreated newAccountCreated) {
        this.accountId = newAccountCreated.getAccountId();
        this.balance = Amount.ZERO;
    }

    public void apply(NewWithdrawMade newWithdrawMade) {
        this.balance = this.balance.minus(newWithdrawMade.getAmount());
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

    public Amount getBalance() {
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
