package com.marmulasse.bank.account.events;

import com.marmulasse.bank.account.Account;

public interface AccountEvent {
    Account apply(Account account);
}
