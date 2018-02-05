package com.marmulasse.bank.account;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountFactoryTest;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.exception.AccountNotFound;
import com.marmulasse.bank.account.port.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WithdrawHandlerShould {

    private WithdrawHandler sut;
    private ArgumentCaptor<Account> accountArgumentCaptor;
    private AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
        accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        accountRepository = mock(AccountRepository.class);
        sut = new WithdrawHandler(accountRepository);
    }

    @Test
    public void store_account_after_deposit() throws Exception {
        Account existingAccount = AccountFactoryTest.existingAccount(AccountId.create());
        AccountId accountId = existingAccount.getProjection().getAccountId();
        when(accountRepository.get(accountId)).thenReturn(Optional.of(existingAccount));

        sut.makeWithdraw(accountId, Amount.of(10.0));

        verify(accountRepository).save(accountArgumentCaptor.capture());
        Account savedAccount = accountArgumentCaptor.getValue();
        assertThat(savedAccount).isNotNull();
    }

    @Test
    public void fail_when_no_account_exist_for_specified_accountId() throws Exception {
        AccountId unknownAccountId = AccountId.create(UUID.fromString("bb5edf47-929e-45b3-98eb-c238c41cf983"));
        when(accountRepository.get(unknownAccountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.makeWithdraw(unknownAccountId, Amount.of(10.0)))
                .hasMessageContaining("Account not found from accountId AccountId{value=bb5edf47-929e-45b3-98eb-c238c41cf983}")
                .isInstanceOf(AccountNotFound.class);
    }

}
