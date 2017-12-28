package com.marmulasse.bank.account;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.commands.CreateAccountCommand;
import com.marmulasse.bank.account.commands.handlers.CreateAccountCommandHandler;
import com.marmulasse.bank.account.port.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateAccountCommandHandlerShould {

    private CreateAccountCommandHandler sut;

    private AccountRepository accountRepositoryMock;

    @Before
    public void setUp() throws Exception {
        accountRepositoryMock = mock(AccountRepository.class);
        sut = new CreateAccountCommandHandler(accountRepositoryMock);
    }

    @Test
    public void create_a_new_account() throws Exception {
        AccountId accountId = AccountId.create();
        sut.handle(new CreateAccountCommand(accountId));

        ArgumentCaptor<Account> createdAccountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryMock).save(createdAccountCaptor.capture());
        Account newAccount = createdAccountCaptor.getValue();
        assertThat(newAccount.getAccountId()).isEqualTo(newAccount.getAccountId());
        assertThat(newAccount.getBalance()).isEqualTo(Amount.ZERO);
    }
}
