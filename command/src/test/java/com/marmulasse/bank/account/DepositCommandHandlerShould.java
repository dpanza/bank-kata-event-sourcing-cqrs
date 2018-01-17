package com.marmulasse.bank.account;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.commands.MakeDepositCommand;
import com.marmulasse.bank.account.commands.handlers.DepositCommandHandler;
import com.marmulasse.bank.account.port.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DepositCommandHandlerShould {

    private DepositCommandHandler sut;

    private AccountRepository accountRepositoryMock;

    @Before
    public void setUp() throws Exception {
        accountRepositoryMock = mock(AccountRepository.class);
        sut = new DepositCommandHandler(accountRepositoryMock);
    }

    @Test
    public void make_a_new_deposit_on_existing_account() throws Exception {
        Account existingAccount = Account.empty();
        when(accountRepositoryMock.get(existingAccount.getAccountId())).thenReturn(Optional.of(existingAccount));

        sut.handle(new MakeDepositCommand(existingAccount.getAccountId(), Amount.of(1.0)));

        ArgumentCaptor<Account> updatedAccountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryMock).save(updatedAccountCaptor.capture());
        Account updated = updatedAccountCaptor.getValue();
        assertThat(updated.getAccountId()).isEqualTo(existingAccount.getAccountId());
        assertThat(updated.getBalance()).isEqualTo(Amount.of(1.0));
    }

    @Test
    public void fail_when_no_account_found_for_specified_account_id() throws Exception {
        AccountId unknownAccountId = AccountId.from("bb5edf47-929e-45b3-98eb-c238c41cf983");
        when(accountRepositoryMock.get(unknownAccountId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> sut.handle(new MakeDepositCommand(unknownAccountId, Amount.of(1.0))))
                .hasMessageContaining("Account not found from accountId AccountId{value=bb5edf47-929e-45b3-98eb-c238c41cf983}")
                .isInstanceOf(RuntimeException.class);
    }
}
