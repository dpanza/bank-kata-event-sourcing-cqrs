package com.marmulasse.bank.account.commands.handlers;

import com.marmulasse.bank.account.aggregate.Account;
import com.marmulasse.bank.account.commands.CreateAccountCommand;
import com.marmulasse.bank.account.port.AccountRepository;

public class CreateAccountCommandHandler implements CommandHandler<CreateAccountCommand> {
    private AccountRepository accountRepository;

    public CreateAccountCommandHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Class<CreateAccountCommand> listenTo() {
        return CreateAccountCommand.class;
    }

    @Override
    public void handle(CreateAccountCommand command) {
        accountRepository.save(Account.empty(command.getAccountId()));
    }
}
