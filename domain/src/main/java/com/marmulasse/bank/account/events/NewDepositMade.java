package com.marmulasse.bank.account.events;

import com.marmulasse.bank.account.Amount;

public class NewDepositMade implements AccountEvent {
    private Amount amount;

    public NewDepositMade(Amount amount) {
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewDepositMade that = (NewDepositMade) o;

        return amount != null ? amount.equals(that.amount) : that.amount == null;

    }

    @Override
    public int hashCode() {
        return amount != null ? amount.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NewDepositMade{" +
                "amount=" + amount +
                '}';
    }
}
