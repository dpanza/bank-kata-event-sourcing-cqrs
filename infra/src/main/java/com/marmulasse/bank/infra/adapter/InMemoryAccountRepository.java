package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.port.AccountRepository;

import java.util.Map;
import java.util.Optional;

class InMemoryAccountRepository implements AccountRepository {

    public InMemoryAccountRepository() {
    }

    private Map<AccountId, Account> db;

    public InMemoryAccountRepository(Map<AccountId, Account> db) {
        this.db = db;
    }

    @Override
    public void save(Account account) {
        db.put(account.getAccountId(), account);
    }

    @Override
    public Optional<Account> get(AccountId accountId) {
        return Optional.ofNullable(db.getOrDefault(accountId, null));
    }
}