package com.marmulasse.bank.account.aggregate.events;

import com.marmulasse.bank.account.aggregate.Account;

public interface AccountEvent {
    Account apply(Account account);
}
