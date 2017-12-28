package com.marmulasse.bank.infra.bus;

import com.marmulasse.bank.account.commands.Command;
import com.marmulasse.bank.account.commands.handlers.CommandHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandBus {
    private Map<Class<? extends Command>, CommandHandler> handlers;

    public CommandBus(List<CommandHandler> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toMap(CommandHandler::listenTo, ch -> ch));
    }

    public void dispatch(Command command) {
        this.handlers.get(command.getClass())
                .handle(command);
    }
}
