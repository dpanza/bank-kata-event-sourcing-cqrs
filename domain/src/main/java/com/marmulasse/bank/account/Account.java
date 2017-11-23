package com.marmulasse.bank.account;

public class Account {
    private Balance balance;

    public static Account empty() {
        return new Account(Balance.ZERO);
    }

    public static Account with(Balance balance) {
        return new Account(balance);
    }

    private Account(Balance balance) {
        this.balance = balance;
    }

    public void deposit(Amount amount) {
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
