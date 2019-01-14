package org.rubio.klader.core.command.system;

import org.rubio.klader.core.command.types.CommandContext;
import org.rubio.klader.core.command.types.Command;
import org.rubio.klader.core.storage.manager.RegistryManager;

public class InvalidateStorageCommand implements Command {

    @Override
    public void handle(RegistryManager manager, CommandContext context) {
        manager.getStorage().invalidate();
    }

    @Override
    public String getIdentifier() {
        return "invalidate";
    }

    @Override
    public String getName() {
        return "Invalidate storage data";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
