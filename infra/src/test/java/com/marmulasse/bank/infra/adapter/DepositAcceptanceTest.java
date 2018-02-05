package com.marmulasse.bank.infra.adapter;

import com.marmulasse.bank.account.DepositHandler;
import com.marmulasse.bank.account.aggregate.*;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class DepositAcceptanceTest {

    private DepositHandler depositHandler;
    private InMemoryAccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
        accountRepository = new InMemoryAccountRepository();
        depositHandler = new DepositHandler(accountRepository);
    }

    @Test
    public void making_deposit_should_add_amount_to_an_account() throws Exception {
        Account existingAccount = Account.empty();
        assertThat(existingAccount.getBalance()).isEqualTo(Balance.ZERO);
        accountRepository.save(existingAccount);

        depositHandler.makeDeposit(existingAccount.getAccountId(), Amount.of(10.0));

        Account updatedAccount = accountRepository.get(existingAccount.getAccountId()).get();
        assertThat(updatedAccount.getBalance()).isEqualTo(Balance.of(10.0));
    }
}