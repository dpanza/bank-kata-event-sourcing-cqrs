package com.marmulasse.bank.account.commands.handlers;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.aggregate.AccountId;
import com.marmulasse.bank.account.commands.MakeDepositCommand;
import com.marmulasse.bank.account.exception.AccountNotFound;
import com.marmulasse.bank.account.port.AccountRepository;

public class DepositCommandHandler implements CommandHandler<MakeDepositCommand> {
    private AccountRepository accountRepository;

    public DepositCommandHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Class<MakeDepositCommand> listenTo() {
        return MakeDepositCommand.class;
    }

    @Override
    public void handle(MakeDepositCommand command) {
        AccountId accountId = command.getAccountId();
        Account account;
        try {
            account = accountRepository.get(accountId).orElseThrow(() -> new AccountNotFound(accountId));
            account.deposit(command.getAmount());
            accountRepository.save(account);
        } catch (AccountNotFound accountNotFound) {
            throw new RuntimeException(accountNotFound);
        }
    }
}
