package org.rubio.klader.core.command.system;

import org.rubio.klader.core.command.types.CommandContext;
import org.rubio.klader.core.command.types.Command;
import org.rubio.klader.core.storage.RegistryEntry;
import org.rubio.klader.core.storage.StorageRegistry;
import org.rubio.klader.core.storage.manager.RegistryManager;

public class StorageInfoCommand implements Command {
    @Override
    public void handle(RegistryManager manager, CommandContext context) {
        StorageRegistry structure = manager.getStorage().getRegistry();
        for(RegistryEntry item: structure) {
            System.out.println("File: " + item.getFile().getName());
            System.out.println(item.getInfo());
            System.out.println("\n\n");
        }
    }

    @Override
    public String getIdentifier() {
        return "info";
    }

    @Override
    public String getName() {
        return "Show storage info";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
