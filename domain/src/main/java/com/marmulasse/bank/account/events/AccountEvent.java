package com.marmulasse.bank.account.events;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;

public interface AccountEvent {

    AccountId getAccountId();

    String getName();

    void apply(Account account);
}
