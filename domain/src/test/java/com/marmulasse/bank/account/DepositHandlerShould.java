package com.marmulasse.bank.account;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.exception.AccountNotFound;
import com.marmulasse.bank.account.port.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class DepositHandlerShould {

    private DepositHandler sut;

    private AccountRepository accountRepositoryMock;

    @Before
    public void setUp() throws Exception {
        accountRepositoryMock = mock(AccountRepository.class);
        sut = new DepositHandler(accountRepositoryMock);
    }

    @Test
    public void make_a_new_deposit_on_existing_account() throws Exception {
        Account account = Account.empty();
        when(accountRepositoryMock.get(account.getAccountId())).thenReturn(Optional.of(account));

        sut.makeDeposit(account.getAccountId(), Amount.of(10.0));

        verify(accountRepositoryMock).save(any(Account.class));
    }

    @Test
    public void fail_when_no_account_found_for_specified_account_id() throws Exception {
        AccountId unknownAccountId = AccountId.from("bb5edf47-929e-45b3-98eb-c238c41cf983");
        when(accountRepositoryMock.get(unknownAccountId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> sut.makeDeposit(unknownAccountId, Amount.of(10.0)))
                .hasMessageContaining("Account not found from accountId AccountId{value=bb5edf47-929e-45b3-98eb-c238c41cf983}")
                .isInstanceOf(AccountNotFound.class);
    }
}
