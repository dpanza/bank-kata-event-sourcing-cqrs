package com.marmulasse.bank.query.account.queries;

public class Result<T> {

    private T value;

    public Result(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
