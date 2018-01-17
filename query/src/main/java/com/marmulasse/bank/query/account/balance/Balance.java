package com.marmulasse.bank.query.account.balance;

import com.marmulasse.bank.query.account.events.NewDepositMade;
import com.marmulasse.bank.query.account.events.NewWithdrawMade;

public class Balance {

    private AccountId accountId;
    private Amount balance;

    public static Balance create(AccountId accountId) {
        return create(accountId, Amount.empty());
    }

    public static Balance create(AccountId accountId, Amount balance) {
        return new Balance(accountId, balance);
    }

    public Balance(AccountId accountId, Amount balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public Balance add(NewDepositMade event) {
        return Balance.create(accountId, balance.add(event.getAmount()));
    }

    public Balance add(NewWithdrawMade event) {
        return Balance.create(accountId, balance.minus(event.getAmount()));
    }


    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance1 = (Balance) o;

        if (accountId != null ? !accountId.equals(balance1.accountId) : balance1.accountId != null) return false;
        return balance != null ? balance.equals(balance1.balance) : balance1.balance == null;

    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
