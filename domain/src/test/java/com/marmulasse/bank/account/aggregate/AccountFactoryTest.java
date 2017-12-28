package com.marmulasse.bank.account.aggregate;

public class AccountFactoryTest {

    public static Account existingAccount(AccountId accountId) {
        return Account.empty(accountId);
    }

    public static Account accountWithBalance(Amount balance) {
        AccountId accountId = AccountId.create();
        Account emptyAccount = Account.empty(accountId);
        return emptyAccount.deposit(balance);
    }

}
