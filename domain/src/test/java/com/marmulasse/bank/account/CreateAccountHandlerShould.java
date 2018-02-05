package com.marmulasse.bank.account;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.port.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateAccountHandlerShould {

    private CreateAccountHandler sut;
    private AccountRepository accountRepository;
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        accountRepository = mock(AccountRepository.class);
        sut = new CreateAccountHandler(accountRepository);
    }

    @Test
    public void store_a_new_account_in_account_repository() throws Exception {
        sut.createAccount();

        verify(accountRepository).save(accountArgumentCaptor.capture());
        Account savedAccount = accountArgumentCaptor.getValue();
        assertThat(savedAccount).isNotNull();
    }
}