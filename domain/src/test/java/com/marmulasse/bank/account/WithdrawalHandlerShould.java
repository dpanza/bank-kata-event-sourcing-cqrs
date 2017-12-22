package com.marmulasse.bank.account;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.exception.AccountNotFound;
import com.marmulasse.bank.account.port.AccountRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class WithdrawalHandlerShould {

    private WithdrawalHandler sut;

    private AccountRepository accountRepositoryMock;

    @Before
    public void setUp() throws Exception {
        accountRepositoryMock = mock(AccountRepository.class);
        sut = new WithdrawalHandler(accountRepositoryMock);
    }

    @Test
    public void make_a_withdrawal_on_account() throws Exception {
        Account account = spy(Account.empty());
        when(accountRepositoryMock.get(account.getAccountId())).thenReturn(Optional.of(account));

        sut.makeDeposit(account.getAccountId(), Amount.of(10.0));

        verify(account).withdraw(Amount.of(10));
    }

    @Test
    public void store_updated_account_when_withdraw_applied() throws Exception {
        Account account = Account.empty();
        when(accountRepositoryMock.get(account.getAccountId())).thenReturn(Optional.of(account));

        sut.makeDeposit(account.getAccountId(), Amount.of(10.0));

        verify(accountRepositoryMock).save(account);
    }

    @Test
    public void fail_when_no_account_found_for_specified_account_id() throws Exception {
        AccountId unknownAccountId = AccountId.from("bb5edf47-929e-45b3-98eb-c238c41cf983");
        when(accountRepositoryMock.get(unknownAccountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.makeDeposit(unknownAccountId, Amount.of(10.0)))
                .hasMessageContaining("Account not found from accountId AccountId{value=bb5edf47-929e-45b3-98eb-c238c41cf983}")
                .isInstanceOf(AccountNotFound.class);
    }
}
