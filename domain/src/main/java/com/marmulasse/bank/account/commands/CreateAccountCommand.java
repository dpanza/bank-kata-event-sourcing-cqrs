package com.marmulasse.bank.account.commands;

import com.google.common.base.Preconditions;
import com.marmulasse.bank.account.aggregate.AccountId;

public class CreateAccountCommand implements Command {

    private AccountId accountId;

    public CreateAccountCommand(AccountId accountId) {
        Preconditions.checkNotNull(accountId, "An account id is mandatory");
        this.accountId = accountId;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }
}
