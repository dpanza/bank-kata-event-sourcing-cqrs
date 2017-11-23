package com.marmulasse.bank.account;

import com.google.common.base.Preconditions;

import java.util.Objects;

public class Account {
    private AccountId accountId;
    private Balance balance;

    public static Account empty() {
        return new Account(AccountId.create(), Balance.ZERO);
    }

    public static Account with(Balance balance) {
        return new Account(AccountId.create(), balance);
    }

    private Account(AccountId accountId, Balance balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public void deposit(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A deposit must be positive");
        this.balance = this.balance.add(amount);
    }

    public Balance getBalance() {
        return balance;
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
}
