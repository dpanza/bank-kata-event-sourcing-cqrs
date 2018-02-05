package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.WithdrawHandler;
import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.Amount;
import com.marmulasse.bank.account.aggregate.Balance;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class WithdrawAcceptanceTest {

    private WithdrawHandler withdrawHandler;
    private InMemoryAccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
        accountRepository = new InMemoryAccountRepository();
        withdrawHandler = new WithdrawHandler(accountRepository);
    }

    @Test
    public void making_withdraw_should_reduce_balance_of_an_account() throws Exception {
        Account existingAccount = Account.with(Balance.of(15.0));
        assertThat(existingAccount.getProjection().getBalance()).isEqualTo(Balance.ZERO);
        accountRepository.save(existingAccount);

        withdrawHandler.makeWithdraw(existingAccount.getProjection().getAccountId(), Amount.of(10.0));

        Account updatedAccount = accountRepository.get(existingAccount.getProjection().getAccountId()).get();
        assertThat(updatedAccount.getProjection().getBalance()).isEqualTo(Balance.of(5.0));
    }
}