package com.marmulasse.bank.account.queries.handlers;


import com.marmulasse.bank.account.queries.Result;

public interface QueryHandler<T, R> {
    Class<R> resultTo();

    Result<R> handle(T query);
}
