package com.marmulasse.bank.account.aggregate;

import com.marmulasse.bank.account.events.AccountEvent;
import com.marmulasse.bank.account.events.NewAccountCreated;
import com.marmulasse.bank.account.events.NewDepositMade;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccountFactoryTest {

    public static Account existingAccount(AccountId accountId) {
        List<AccountEvent> events = Collections.singletonList(new NewAccountCreated(accountId));
        return create(events);
    }

    public static Account accountWithBalance(Amount balance) {
        AccountId accountId = AccountId.create();
        List<AccountEvent> events = Arrays.asList(
                new NewAccountCreated(accountId),
                new NewDepositMade(accountId, Amount.of(balance.getValue()))
        );
        return create(events);
    }

    private static Account create(List<AccountEvent> events) {
        return Account.rebuildFrom(events).get();
    }
}
