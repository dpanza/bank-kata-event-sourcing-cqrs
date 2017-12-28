package com.marmulasse.bank.account.queries;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;


public class GetAccountFromId implements Query {

    private final AccountId accountId;

    public GetAccountFromId(String id) {
        accountId = AccountId.from(id);
    }

    @Override
    public Class thatReturn() {
        return Account.class;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}
