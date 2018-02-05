package com.marmulasse.bank.account;

import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.port.AccountRepository;

public class DepositHandler {
    private AccountRepository accountRepository;

    public DepositHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void makeDeposit(AccountId accountId, Amount amount) {
    }
}
