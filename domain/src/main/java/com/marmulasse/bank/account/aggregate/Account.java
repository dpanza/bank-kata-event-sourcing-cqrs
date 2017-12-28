package com.marmulasse.bank.account.aggregate;

import com.google.common.base.Preconditions;

public class Account {
    protected Amount balance;
    protected AccountId accountId;

    public static Account empty() {
        return new Account(AccountId.create());
    }

    public static Account empty(AccountId accountId) {
        return new Account(accountId);
    }

    private Account() {
    }

    private Account(AccountId accountId) {
        this.accountId = accountId;
        this.balance = Amount.ZERO;
    }

    public Account deposit(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A deposit must be positive");
        this.balance = this.balance.plus(amount);
        return this;
    }

    public Account withdraw(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A withdraw must be positive");
        this.balance = this.balance.minus(amount);
        return this;
    }

    public AccountId getAccountId() {
        return accountId;
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
        return accountId != null ? accountId.equals(account.accountId) : account.accountId == null;

    }

    @Override
    public int hashCode() {
        int result = balance != null ? balance.hashCode() : 0;
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", accountId=" + accountId +
                '}';
    }

}
