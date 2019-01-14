package org.rubio.klader.core.command.types;

import org.rubio.klader.core.storage.manager.RegistryManager;

import java.util.HashMap;
import java.util.Map;

public class CommandBus implements AtomicBus {

    private Map<Command, CommandContext> commands;
    private RegistryManager manager;

    public CommandBus(RegistryManager manager) {
        this.commands = null;
        this.manager = manager;
    }

    @Override
    public void send(Command command, CommandContext context){
        if (null == commands) {
            throw new OutOfTransactionException();
        }
        commands.put(command, context);
    }

    @Override
    public void rollbackBusTransaction() {
        throw new RuntimeException("Out of implementation");
    }

    @Override
    public void beginBusTransaction() {
        commands = new HashMap<>();
    }

    @Override
    public void commitBusTransaction() {
        for(Command command: commands.keySet()) {
            CommandContext context = commands.get(command);
            command.handle(manager, context);
        }
    }
}
