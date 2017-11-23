package com.marmulasse.bank.account.aggregate;

public class Balance {
    private double value;

    public static Balance ZERO = Balance.of(0.0);

    public static Balance of(Double value) {
        return new Balance(value);
    }

    private Balance(Double value) {
        this.value = value;
    }

    public Balance add(Amount amountToAdd) {
        Amount amount = Amount.of(this.value).add(amountToAdd);
        return new Balance(amount.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        return Double.compare(balance.value, value) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "Balance{" +
                "value=" + value +
                '}';
    }
}
