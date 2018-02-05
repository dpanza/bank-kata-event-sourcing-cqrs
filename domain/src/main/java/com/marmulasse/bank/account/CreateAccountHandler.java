package com.marmulasse.bank.account;

import com.marmulasse.bank.account.port.AccountRepository;

public class CreateAccountHandler {
    private AccountRepository accountRepository;

    public CreateAccountHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount() {
    }
}
