package org.rubio.klader.core.command.system;

import org.rubio.klader.core.command.types.CommandContext;
import org.rubio.klader.core.command.types.Command;
import org.rubio.klader.core.storage.manager.RegistryManager;

import java.io.PrintStream;

public class CountItemsCommand implements Command {
    @Override
    public void handle(RegistryManager manager, CommandContext context) {
        ((PrintStream)context.getOutput()).println(manager.getRepository(context.getOptions()).getCount());
    }

    @Override
    public String getIdentifier() {
        return "count";
    }

    @Override
    public String getName() {
        return "Count kladr items (possible values: city, street)";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
