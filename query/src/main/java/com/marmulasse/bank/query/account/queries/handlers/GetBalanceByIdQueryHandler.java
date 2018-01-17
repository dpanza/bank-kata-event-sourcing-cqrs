package com.marmulasse.bank.query.account.queries.handlers;

import com.marmulasse.bank.query.account.queries.GetBalanceFromAccountId;
import com.marmulasse.bank.query.account.queries.Result;
import com.marmulasse.bank.query.account.balance.Balance;
import com.marmulasse.bank.query.account.port.BalanceRepository;
import com.marmulasse.bank.query.account.port.QueryHandler;

public class GetBalanceByIdQueryHandler implements QueryHandler<GetBalanceFromAccountId, Balance> {

    public BalanceRepository balanceRepository;

    public GetBalanceByIdQueryHandler(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public Class<Balance> resultTo() {
        return Balance.class;
    }

    public Result<Balance> handle(GetBalanceFromAccountId query) {
        return balanceRepository.get(query.getAccountId())
                .map(account -> new Result(account))
                .orElse(new Result(null));
    }
}
