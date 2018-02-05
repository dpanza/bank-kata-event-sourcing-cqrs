package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.port.AccountRepository;

import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {

    public void save(Account account) {

    }

    public Optional<Account> get(AccountId accountId) {
        return null;
    }
}
