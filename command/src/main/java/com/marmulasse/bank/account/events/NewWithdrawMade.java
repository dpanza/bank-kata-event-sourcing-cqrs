package com.marmulasse.bank.account.events;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;

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

    @Override
    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public String getName() {
        return "NewWithdrawMade";
    }

    @Override
    public void apply(Account account) {
        account.apply(this);
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
        return "NewWithdrawMade{" +
                "accountId=" + accountId +
                ", amount=" + amount +
                '}';
    }
}
