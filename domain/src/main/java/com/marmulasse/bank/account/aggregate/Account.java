package com.marmulasse.bank.account.aggregate;

import com.google.common.base.Preconditions;
import com.marmulasse.bank.account.aggregate.events.AccountEvent;
import com.marmulasse.bank.account.aggregate.events.NewAccountCreated;
import com.marmulasse.bank.account.aggregate.events.NewDepositMade;
import com.marmulasse.bank.account.aggregate.events.NewWithdrawMade;

import java.util.List;

public class Account extends EventSourcedAggregate<AccountProjection> {

    public static Account empty() {
        return new Account(AccountId.create(), Balance.ZERO);
    }

    public static Account with(Balance balance) {
        return new Account(AccountId.create(), balance);
    }

    public Account(List<AccountEvent<AccountProjection>> changes) {
        super(new AccountProjection(), changes);
    }

    private Account(AccountId accountId, Balance balance) {
        super(new AccountProjection());
        NewAccountCreated newAccountCreated = new NewAccountCreated(accountId, balance);
        applyDecision(newAccountCreated);
    }

    public void deposit(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A deposit must be positive");
        NewDepositMade newDepositMade = new NewDepositMade(projection.getAccountId(), amount);
        applyDecision(newDepositMade);
    }

    public void withdraw(Amount amount) {
        Preconditions.checkArgument(amount.isPositive(), "A withdraw must be positive");
        NewWithdrawMade newWithdrawMade = new NewWithdrawMade(projection.getAccountId(), amount);
        applyDecision(newWithdrawMade);
    }
}
