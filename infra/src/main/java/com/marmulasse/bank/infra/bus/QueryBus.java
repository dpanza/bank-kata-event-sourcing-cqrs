package com.marmulasse.bank.infra.bus;

import com.marmulasse.bank.query.account.port.QueryHandler;
import com.marmulasse.bank.query.account.queries.Query;
import com.marmulasse.bank.query.account.queries.Result;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class QueryBus {
    private Map<Class, QueryHandler> handlers;

    public QueryBus(List<QueryHandler> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toMap(QueryHandler::resultTo, ch -> ch));
    }

    public Result ask(Query query) {
        return this.handlers.get(query.thatReturn()).handle(query);
    }
}
