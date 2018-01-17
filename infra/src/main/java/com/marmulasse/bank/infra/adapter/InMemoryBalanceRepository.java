package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.query.account.balance.AccountId;
import com.marmulasse.bank.query.account.balance.Balance;
import com.marmulasse.bank.query.account.port.BalanceRepository;

import java.util.Map;
import java.util.Optional;

class InMemoryBalanceRepository implements BalanceRepository {

    public InMemoryBalanceRepository() {
    }

    private Map<AccountId, Balance> db;

    public InMemoryBalanceRepository(Map<AccountId, Balance> db) {
        this.db = db;
    }

    @Override
    public void save(Balance balance) {
        db.put(balance.getAccountId(), balance);
    }

    @Override
    public Optional<Balance> get(com.marmulasse.bank.query.account.balance.AccountId accountId) {
        return Optional.ofNullable(db.getOrDefault(accountId, null));
    }
}