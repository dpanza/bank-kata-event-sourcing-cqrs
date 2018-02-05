package com.marmulasse.bank.account.exception;

import com.marmulasse.bank.account.aggregate.AccountId;

public class AccountNotFound extends Exception {
    public AccountNotFound(AccountId accountId) {
        super("Account not found from accountId " + accountId);
    }
}
