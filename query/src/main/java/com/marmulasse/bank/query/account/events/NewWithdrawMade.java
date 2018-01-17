package com.marmulasse.bank.query.account.events;

import com.marmulasse.bank.query.account.balance.AccountId;
import com.marmulasse.bank.query.account.balance.Amount;

public class NewWithdrawMade implements AccountEvent {
    private AccountId accountId;
    private Amount amount;

    public NewWithdrawMade() {
    }

    public NewWithdrawMade(AccountId accountId, Amount amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewWithdrawMade that = (NewWithdrawMade) o;

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
}
