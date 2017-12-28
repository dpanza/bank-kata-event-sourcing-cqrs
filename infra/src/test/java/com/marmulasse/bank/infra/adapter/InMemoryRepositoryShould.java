package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountFactoryTest;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryRepositoryShould {

    private InMemoryAccountRepository sut;
    private HashMap<AccountId, Account> persistedMap;

    @Before
    public void setUp() throws Exception {
        persistedMap = new HashMap<>();
        sut = new InMemoryAccountRepository(persistedMap);
    }

    @Test
    public void persist_an_account_in_persisted_map() throws Exception {
        Account account = AccountFactoryTest.existingAccount(AccountId.from("5343478c-812a-49c5-acbc-d75ce70acdff"));
        account.deposit(Amount.of(10.0));

        sut.save(account);

        assertThat(persistedMap).containsEntry(account.getAccountId(), account);
    }

    @Test
    public void return_no_account_when_account_not_in_persisted_map() throws Exception {
        Optional<Account> account = sut.get(AccountId.from("568ec631-8275-49b1-89cd-c9bbb4e5376b"));

        assertThat(account).isNotPresent();
    }

    @Test
    public void return_a_persisted_account() throws Exception {
        AccountId accountId = AccountId.from("568ec631-8275-49b1-89cd-c9bbb4e5376b");
        Account emptyAccount = Account.empty(accountId);
        persistedMap.put(accountId, emptyAccount);

        Optional<Account> account = sut.get(accountId);

        assertThat(account).isPresent().contains(emptyAccount);
    }
}