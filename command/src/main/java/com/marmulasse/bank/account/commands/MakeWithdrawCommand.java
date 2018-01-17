package com.marmulasse.bank.account.commands;

import com.google.common.base.Preconditions;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.aggregate.Amount;

public class MakeWithdrawCommand implements Command {
    private AccountId accountId;
    private Amount amount;

    public MakeWithdrawCommand(AccountId accountId, Amount amount) {
        Preconditions.checkNotNull(accountId);
        Preconditions.checkNotNull(amount);
        Preconditions.checkArgument(amount.isPositive(), "Amount must be positive for a withdraw");
        this.accountId = accountId;
        this.amount = amount;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public Amount getAmount() {
        return amount;
    }

}
