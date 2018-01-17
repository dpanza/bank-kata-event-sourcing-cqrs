package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.events.AccountEvent;
import com.marmulasse.bank.account.port.AccountRepository;
import com.marmulasse.bank.infra.bus.DomainBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class InMemoryAccountRepository implements AccountRepository {

    public InMemoryAccountRepository() {
    }

    private Map<AccountId, List<AccountEvent>> db;
    private DomainBus domainBus;

    public InMemoryAccountRepository(Map<AccountId, List<AccountEvent>> db, DomainBus domainBus) {
        this.db = db;
        this.domainBus = domainBus;
    }

    @Override
    public void save(Account account) {
        List<AccountEvent> accountEventsStored = db.getOrDefault(account.getAccountId(), new ArrayList<>());
        accountEventsStored.addAll(account.getNewChanges());
        db.put(account.getAccountId(), accountEventsStored);

        account.getNewChanges().forEach(accountEvent -> domainBus.dispatch(accountEvent));
    }

    @Override
    public Optional<Account> get(AccountId accountId) {
        List<AccountEvent> accountEventsStored = db.getOrDefault(accountId, new ArrayList<>());
        return Account.rebuildFrom(accountEventsStored);
    }
}