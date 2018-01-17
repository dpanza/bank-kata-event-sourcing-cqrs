package com.marmulasse.bank.account.aggregate;

public class AccountFactoryTest {

    public static Account existingAccount(AccountId accountId) {
        return Account.existing(accountId);
    }

    public static Account accountWithBalance(Amount balance) {
        Account emptyAccount = Account.empty();
        emptyAccount.deposit(balance);
        return emptyAccount;
    }

}
