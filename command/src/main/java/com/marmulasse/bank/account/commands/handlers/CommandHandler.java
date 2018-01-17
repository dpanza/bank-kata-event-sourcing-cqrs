package com.marmulasse.bank.account.commands.handlers;

import com.marmulasse.bank.account.commands.Command;

public interface CommandHandler<T extends Command> {
    Class<T> listenTo();

    void handle(T command);
}
