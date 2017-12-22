package com.marmulasse.bank.account.aggregate;

import com.marmulasse.bank.account.events.NewAccountCreated;
import com.marmulasse.bank.account.events.NewDepositMade;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class AccountShould {

    @Test
    public void have_a_zero_balance_when_initialized() throws Exception {
        Account account = Account.empty();

        assertThat(account.getBalance()).isEqualTo(Amount.ZERO);
    }

    @Test
    public void add_amount_to_empty_account_when_deposit_is_made() throws Exception {
        Account account = Account.empty();

        account.deposit(Amount.of(10.0));

        assertThat(account.balance).isEqualTo(Amount.of(10.0));
    }

    @Test
    public void add_amount_to_non_zero_balance_when_deposit_is_made() throws Exception {
        Account account = AccountFactoryTest.accountWithBalance(Amount.of(10.0));

        account.deposit(Amount.of(5.0));

        assertThat(account.balance).isEqualTo(Amount.of(15.0));
    }

    @Test
    public void add_new_account_creation_event_when_account_is_created() throws Exception {
        Account account = Account.empty();

        assertThat(account.getNewChanges()).containsExactly(new NewAccountCreated(account.getAccountId()));
    }

    @Test
    public void add_new_deposit_event_when_deposit_is_made() throws Exception {
        Account account = Account.empty();

        account.deposit(Amount.of(1.0));

        assertThat(account.getNewChanges())
                .containsExactly(
                        new NewAccountCreated(account.getAccountId()),
                        new NewDepositMade(account.getAccountId(), Amount.of(1.0)));
    }

    @Test
    public void be_identified_by_an_account_id_when_created() throws Exception {
        Account account = Account.empty();

        assertThat(account.getAccountId()).isNotNull();
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

    @Test
    public void minus_amount_from_empty_account_when_withdraw_is_made() throws Exception {
        Account account = Account.empty();

        account.withdraw(Amount.of(10.0));

        assertThat(account.balance).isEqualTo(Amount.of(-10.0));
    }

    @Test
    public void fail_when_withdraw_a_negative_amount() throws Exception {
        Account account = Account.empty();

        assertThatThrownBy(() -> account.withdraw(Amount.of(-10.0)))
                .hasMessage("A withdraw must be positive");
    }

    @Test
    public void fail_when_withdraw_a_zero_amount() throws Exception {
        Account account = Account.empty();

        assertThatThrownBy(() -> account.withdraw(Amount.of(0)))
                .hasMessage("A withdraw must be positive");
    }
}
