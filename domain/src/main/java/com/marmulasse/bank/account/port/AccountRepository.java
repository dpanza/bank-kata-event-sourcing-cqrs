package com.marmulasse.bank.account.port;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;

import java.util.Optional;

public interface AccountRepository {
    void save(Account account);

    Optional<Account> get(AccountId accountId);
}
