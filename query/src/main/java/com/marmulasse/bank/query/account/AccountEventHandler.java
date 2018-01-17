package com.marmulasse.bank.query.account;

import com.marmulasse.bank.query.account.balance.AccountId;
import com.marmulasse.bank.query.account.balance.Balance;
import com.marmulasse.bank.query.account.events.NewAccountCreated;
import com.marmulasse.bank.query.account.events.NewDepositMade;
import com.marmulasse.bank.query.account.events.NewWithdrawMade;
import com.marmulasse.bank.query.account.port.BalanceRepository;

public class AccountEventHandler {

    private BalanceRepository balanceRepository;

    public AccountEventHandler(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public void newAccountCreated(NewAccountCreated event) {
        balanceRepository.save(Balance.create(event.getAccountId()));
    }

    public void aDepositMade(NewDepositMade event) {
        AccountId accountId = event.getAccountId();
        Balance balance = balanceRepository.get(event.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found from id " + accountId))
                .add(event);
        balanceRepository.save(balance);
    }

    public void aWithdrawalMade(NewWithdrawMade event) {
        AccountId accountId = event.getAccountId();
        Balance balance = balanceRepository.get(event.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found from id " + accountId))
                .add(event);
        balanceRepository.save(balance);
    }

}
