package com.marmulasse.bank.account.queries.handlers;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.port.AccountRepository;
import com.marmulasse.bank.account.queries.GetAccountFromId;
import com.marmulasse.bank.account.queries.Result;

public class GetAccountByIdQueryHandler implements QueryHandler<GetAccountFromId, Account> {

    public AccountRepository accountRepository;

    public GetAccountByIdQueryHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Class<Account> resultTo() {
        return Account.class;
    }

    @Override
    public Result<Account> handle(GetAccountFromId query) {
        return accountRepository.get(query.getAccountId())
                .map(account -> new Result(account))
                .orElse(new Result(null));
    }
}
