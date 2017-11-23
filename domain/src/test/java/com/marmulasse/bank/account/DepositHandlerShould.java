package com.marmulasse.bank.account;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.aggregate.Balance;
import com.marmulasse.bank.account.events.NewAccountCreated;
import com.marmulasse.bank.account.events.NewDepositMade;
import com.marmulasse.bank.account.port.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DepositHandlerShould {

    private DepositHandler sut;

    private AccountRepository accountRepositoryMock;
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        accountRepositoryMock = mock(AccountRepository.class);
        sut = new DepositHandler(accountRepositoryMock);
        accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
    }

    @Test
    public void persist_a_deposit_in_account() throws Exception {
        sut.makeDeposit(Amount.of(10.0));

        verify(accountRepositoryMock).save(accountArgumentCaptor.capture());
        Account savedAccount = accountArgumentCaptor.getValue();
        assertThat(savedAccount.getNewChanges()).containsExactly(
                new NewAccountCreated(Balance.ZERO),
                new NewDepositMade(Amount.of(10.0)));
    }

}
