package com.marmulasse.bank.query.account.port;


import com.marmulasse.bank.query.account.queries.Result;

public interface QueryHandler<T, R> {
    Class<R> resultTo();

    Result<R> handle(T query);
}
