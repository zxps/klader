package org.rubio.klader.core.command.system;

import org.rubio.klader.core.command.types.CommandContext;
import org.rubio.klader.core.command.types.Command;
import org.rubio.klader.core.storage.manager.RegistryManager;

public class RenewStorageCommand implements Command {
    @Override
    public void handle(RegistryManager manager, CommandContext context) {
        manager.getStorage().renew();
    }

    @Override
    public String getIdentifier() {
        return "renew";
    }

    @Override
    public String getName() {
        return "Renew klader storage data (update Kladr files)";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
