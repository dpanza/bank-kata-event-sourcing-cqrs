package com.marmulasse.bank.query.account.queries;


import com.marmulasse.bank.query.account.balance.AccountId;
import com.marmulasse.bank.query.account.balance.Balance;

public class GetBalanceFromAccountId implements Query {

    private final AccountId accountId;

    public GetBalanceFromAccountId(String id) {
        accountId = AccountId.from(id);
    }

    @Override
    public Class thatReturn() {
        return Balance.class;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}
