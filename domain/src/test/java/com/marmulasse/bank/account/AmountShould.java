package com.marmulasse.bank.account;

import com.marmulasse.bank.account.aggregate.Amount;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AmountShould {

    @Test
    public void be_initialize_with_positive_value() throws Exception {
        Amount amount = Amount.of(0);
        assertThat(amount.getValue()).isEqualTo(0);
    }


    @Test
    public void add_two_amount() throws Exception {
        Amount amount1 = Amount.of(1.0);
        Amount amount2 = Amount.of(1.0);
        assertThat(amount1.add(amount2)).isEqualTo(Amount.of(2.0));
    }
}