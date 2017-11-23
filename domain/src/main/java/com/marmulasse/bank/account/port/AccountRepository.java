package com.marmulasse.bank.account.port;

import com.marmulasse.bank.account.aggregate.Account;

public interface AccountRepository {
    void save(Account account);
}
