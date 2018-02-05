package com.marmulasse.bank.account.aggregate.events;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;

import java.util.Objects;

public class NewWithdrawMade implements AccountEvent {
    private AccountId accountId;
    private Amount amount;

    public NewWithdrawMade(AccountId accountId, Amount amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewWithdrawMade that = (NewWithdrawMade) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, amount);
    }

    @Override
    public String toString() {
        return "NewWithdrawMade{" +
                "accountId=" + accountId +
                ", amount=" + amount +
                '}';
    }

    @Override
    public Account apply(Account account) {
        return account.apply(this);
    }
}
