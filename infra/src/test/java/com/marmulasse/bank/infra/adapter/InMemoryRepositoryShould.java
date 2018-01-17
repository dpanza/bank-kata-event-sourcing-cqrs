package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountFactoryTest;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.events.AccountEvent;
import com.marmulasse.bank.account.events.NewAccountCreated;
import com.marmulasse.bank.account.events.NewDepositMade;
import com.marmulasse.bank.infra.bus.DomainBus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class InMemoryRepositoryShould {

    private InMemoryAccountRepository sut;
    private HashMap<AccountId, List<AccountEvent>> persistedMap;
    private DomainBus domainBus;

    @Before
    public void setUp() throws Exception {
        persistedMap = new HashMap<>();
        domainBus = mock(DomainBus.class);
        sut = new InMemoryAccountRepository(persistedMap, domainBus);
    }

    @Test
    public void persist_an_account_in_persisted_map() throws Exception {
        Account account = AccountFactoryTest.existingAccount(AccountId.from("5343478c-812a-49c5-acbc-d75ce70acdff"));
        account.deposit(Amount.of(10.0));

        sut.save(account);

        assertThat(persistedMap).containsEntry(account.getAccountId(), account.getNewChanges());
    }

    @Test
    public void dispatch_changes_in_domain_bus() throws Exception {
        Account account = AccountFactoryTest.existingAccount(AccountId.from("5343478c-812a-49c5-acbc-d75ce70acdff"));
        account.deposit(Amount.of(10.0));

        sut.save(account);

        verify(domainBus, times(2)).dispatch(or(any(NewAccountCreated.class), any(NewDepositMade.class)));
    }

    @Test
    public void return_no_account_when_account_not_in_persisted_map() throws Exception {
        Optional<Account> account = sut.get(AccountId.from("568ec631-8275-49b1-89cd-c9bbb4e5376b"));

        assertThat(account).isNotPresent();
    }

    @Test
    public void return_a_persisted_account() throws Exception {
        AccountId accountId = AccountId.from("568ec631-8275-49b1-89cd-c9bbb4e5376b");
        Account emptyAccount = Account.existing(accountId);
        persistedMap.put(accountId, emptyAccount.getNewChanges());

        Optional<Account> account = sut.get(accountId);

        assertThat(account).isPresent();
    }
}