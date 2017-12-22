package com.marmulasse.bank.account.aggregate;

public class Amount {
    public static Amount ZERO = Amount.of(0);
    private final double value;

    public static Amount of(double value) {
        return new Amount(value);
    }

    public Amount() {
        value = 0;
    }

    private Amount(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
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

    public Amount plus(Amount amount) {
        return new Amount(this.value + amount.value);
    }

    public Amount minus(Amount amount) {
        return new Amount(this.value - amount.value);
    }

    public boolean isPositive() {
        return value > 0;
    }
}
