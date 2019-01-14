package org.rubio.klader.core.command.types;

public interface Bus {

    public void send(Command command, CommandContext context);

}
