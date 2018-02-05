package com.marmulasse.bank.account.aggregate.events;

import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.AccountProjection;
import com.marmulasse.bank.account.aggregate.Amount;

public class NewDepositMade implements AccountEvent<AccountProjection> {
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
    public AccountProjection apply(AccountProjection projection) {
        return projection.stateApplier().apply(this);
    }
}
