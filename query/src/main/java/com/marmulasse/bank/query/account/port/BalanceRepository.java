package com.marmulasse.bank.query.account.port;

import com.marmulasse.bank.query.account.balance.AccountId;
import com.marmulasse.bank.query.account.balance.Balance;

import java.util.Optional;

public interface BalanceRepository {
    void save(Balance balance);

    Optional<Balance> get(AccountId accountId);
}
