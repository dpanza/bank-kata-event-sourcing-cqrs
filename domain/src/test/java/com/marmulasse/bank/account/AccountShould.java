package com.marmulasse.bank.account;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AccountShould {

    @Test
    public void add_amount_to_empty_account_when_deposit_is_made() throws Exception {
        Account account = Account.empty();

        account.deposit(Amount.of(10.0));

        assertThat(account.getBalance()).isEqualTo(Balance.of(10.0));
    }

    @Test
    public void add_amount_to_non_zero_balance_when_deposit_is_made() throws Exception {
        Account account = Account.with(Balance.of(10.0));

        account.deposit(Amount.of(5.0));

        assertThat(account.getBalance()).isEqualTo(Balance.of(15.0));
    }


}
