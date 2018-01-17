package com.marmulasse.bank.query.account.balance;

public class Amount {
    public static final Amount ZERO = new Amount(0.0);

    private final double value;

    public static Amount empty() {
        return ZERO;
    }

    public static Amount of(double value) {
        return new Amount(value);
    }

    public Amount() {
        value = 0;
    }

    private Amount(double value) {
        this.value = value;
    }

    public Amount add(Amount other) {
        return new Amount(value + other.value);
    }

    public Amount minus(Amount other) {
        return new Amount(value - other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Amount amount = (Amount) o;

        return Double.compare(amount.value, value) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                '}';
    }
}
