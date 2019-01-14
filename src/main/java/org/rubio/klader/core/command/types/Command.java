package org.rubio.klader.core.command.types;

import org.rubio.klader.core.storage.manager.RegistryManager;

public interface Command {

    public void handle(RegistryManager manager, CommandContext context);

    public String getIdentifier();

    public String getName();

    public String getDescription();
}
