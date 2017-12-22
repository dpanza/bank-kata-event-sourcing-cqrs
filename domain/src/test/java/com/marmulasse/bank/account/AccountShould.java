package com.marmulasse.bank.account;

import com.marmulasse.bank.account.events.NewAccountCreated;
import com.marmulasse.bank.account.events.NewDepositMade;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class AccountShould {

    @Test
    public void have_a_zero_balance_when_initialized() throws Exception {
        Account account = Account.empty();

        assertThat(account.getBalance()).isEqualTo(Balance.ZERO);
    }

    @Test
    public void add_amount_to_empty_account_when_deposit_is_made() throws Exception {
        Account account = Account.empty();

        account.deposit(Amount.of(10.0));

        assertThat(account.balance).isEqualTo(Balance.of(10.0));
    }

    @Test
    public void add_amount_to_non_zero_balance_when_deposit_is_made() throws Exception {
        Account account = Account.empty();

        account.deposit(Amount.of(10.0));
        account.deposit(Amount.of(5.0));

        assertThat(account.balance).isEqualTo(Balance.of(15.0));
    }

    @Test
    public void add_new_deposit_event_when_deposit_is_made() throws Exception {
        Account account = Account.empty();

        account.deposit(Amount.of(1.0));

        assertThat(account.getNewChanges())
                .containsExactly(
                        new NewAccountCreated(Balance.ZERO),
                        new NewDepositMade(Amount.of(1.0)));
    }

    @Test
    public void fail_when_deposit_a_negative_amount() throws Exception {
        Account account = Account.empty();

        assertThatThrownBy(() -> account.deposit(Amount.of(-1.0)))
            .hasMessage("A deposit must be positive");
    }

    @Test
    public void fail_when_deposit_a_zero_amount() throws Exception {
        Account account = Account.empty();

        assertThatThrownBy(() -> account.deposit(Amount.of(0.0)))
                .hasMessage("A deposit must be positive");
    }
}
