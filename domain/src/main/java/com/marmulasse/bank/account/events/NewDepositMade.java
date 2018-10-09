package com.marmulasse.bank.account.events;

import com.marmulasse.bank.account.Account;
import com.marmulasse.bank.account.AccountId;
import com.marmulasse.bank.account.Amount;

public class NewDepositMade implements AccountEvent {
    private AccountId accountId;
    private Amount amount;

    public NewDepositMade(AccountId accountId, Amount amount) {
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

        NewDepositMade that = (NewDepositMade) o;

        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        return amount != null ? amount.equals(that.amount) : that.amount == null;

    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewDepositMade{" +
                "accountId=" + accountId +
                ", amount=" + amount +
                '}';
    }

    @Override
    public Account apply(Account account) {
        return account.apply(this);
    }
}
