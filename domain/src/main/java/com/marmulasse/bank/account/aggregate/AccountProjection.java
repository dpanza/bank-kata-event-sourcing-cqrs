package com.marmulasse.bank.account.aggregate;

import com.marmulasse.bank.account.aggregate.events.NewAccountCreated;
import com.marmulasse.bank.account.aggregate.events.NewDepositMade;
import com.marmulasse.bank.account.aggregate.events.NewWithdrawMade;

public class AccountProjection {
    private AccountId accountId;
    private Balance balance;

    public AccountId getAccountId() {
        return accountId;
    }

    public Balance getBalance() {
        return balance;
    }

    public StateApplier stateApplier() {
        return new StateApplier(this);
    }

    public class StateApplier {
        private AccountProjection projection;

        public StateApplier(AccountProjection projection) {
            this.projection = projection;
        }

        public AccountProjection apply(NewDepositMade newDepositMade) {
            projection.balance = projection.balance.add(newDepositMade.getAmount());
            return projection;
        }

        public AccountProjection apply(NewAccountCreated newAccountCreated) {
            projection.accountId = newAccountCreated.getAccountId();
            projection.balance = newAccountCreated.getBalance();
            return projection;
        }

        public AccountProjection apply(NewWithdrawMade newWithdrawMade) {
            projection.balance = projection.balance.minus(newWithdrawMade.getAmount());
            return projection;
        }

    }
}
