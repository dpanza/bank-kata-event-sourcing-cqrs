package com.marmulasse.bank.infra.adapter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.events.AccountEvent;
import com.marmulasse.bank.account.port.AccountRepository;

import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {

    private ListMultimap<AccountId, AccountEvent> db;

    public InMemoryAccountRepository() {
        this(ArrayListMultimap.create());
    }

    public InMemoryAccountRepository(ListMultimap<AccountId, AccountEvent> db) {
        this.db = db;
    }

    public void save(final Account account) {
        account.getUncommittedChanged()
                .forEach(uncommittedChanged -> db.put(account.getAccountId(), uncommittedChanged));
    }

    public Optional<Account> get(AccountId accountId) {
        return Optional.ofNullable(db.get(accountId)).map(Account::rebuild);
    }
}
