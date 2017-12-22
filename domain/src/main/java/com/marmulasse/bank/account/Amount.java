package com.marmulasse.bank.account;

public class Amount {
    private final double value;

    public static Amount of(double value) {
        return new Amount(value);
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

    public Amount add(Amount amount) {
        return new Amount(this.value + amount.value);
    }

    public boolean isPositive() {
        return value > 0;
    }
}
