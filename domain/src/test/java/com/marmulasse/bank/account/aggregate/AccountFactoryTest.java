package com.marmulasse.bank.account.aggregate;

import com.marmulasse.bank.account.aggregate.events.AccountEvent;
import com.marmulasse.bank.account.aggregate.events.NewAccountCreated;

import java.util.Collections;
import java.util.List;

public class AccountFactoryTest {

    public static Account existingAccount(AccountId accountId) {
        List<AccountEvent> events = Collections.singletonList(new NewAccountCreated(accountId, Balance.ZERO));
        return create(events);
    }

    public static Account accountWithBalance(Balance balance) {
        AccountId accountId = AccountId.create();
        List<AccountEvent> events = Collections.singletonList(
                new NewAccountCreated(accountId, balance)
        );
        return create(events);
    }

    private static Account create(List<AccountEvent> events) {
        return Account.rebuild(events);
    }
}
