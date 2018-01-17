package com.marmulasse.bank.account.commands.handlers;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.commands.MakeWithdrawCommand;
import com.marmulasse.bank.account.exception.AccountNotFound;
import com.marmulasse.bank.account.port.AccountRepository;

public class WithdrawCommandHandler implements CommandHandler<MakeWithdrawCommand> {
    private AccountRepository accountRepository;

    public WithdrawCommandHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Class<MakeWithdrawCommand> listenTo() {
        return MakeWithdrawCommand.class;
    }

    @Override
    public void handle(MakeWithdrawCommand command) {
        AccountId accountId = command.getAccountId();
        Account account;
        try {
            account = accountRepository.get(accountId).orElseThrow(() -> new AccountNotFound(accountId));
            account.withdraw(command.getAmount());
            accountRepository.save(account);
        } catch (AccountNotFound accountNotFound) {
            throw new RuntimeException(accountNotFound);
        }
    }
}
