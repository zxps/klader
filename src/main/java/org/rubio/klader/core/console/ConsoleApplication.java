package org.rubio.klader.core.console;

import org.apache.commons.cli.*;
import org.rubio.klader.core.Application;
import org.rubio.klader.core.command.system.*;
import org.rubio.klader.core.command.types.AtomicBus;
import org.rubio.klader.core.command.types.Bus;
import org.rubio.klader.core.command.types.CommandContext;
import org.rubio.klader.core.command.types.Command;

import java.io.OutputStream;
import java.util.*;

public class ConsoleApplication extends Application {

    private Options options;
    private List<Command> commands;
    private OutputStream output;

    public ConsoleApplication(OutputStream output) {
        super();
        this.options = new Options();
        this.commands = new ArrayList<>();
        this.registerSystemCommands();
        this.output = output;
    }

    private void registerSystemCommands() {
        commands.add(new InvalidateStorageCommand());
        commands.add(new StorageInfoCommand());
        commands.add(new RenewStorageCommand());
        commands.add(new CountItemsCommand());
        commands.add(new QueryCommand());
    }

    public void registerCommand (Command command) {
        commands.add(command);
    }

    public Options getOptions() {
        return this.options;
    }

    public void run(String[] args) throws ParseException {
        for(Command command: commands) {
            Option option = Option
                    .builder(command.getIdentifier())
                    .hasArg()
                    .optionalArg(true)
                    .required(false)
                    .argName(command.getIdentifier())
                    .desc(command.getName())
                    .build();
            options.addOption(option);
        }

        options.addOption("v", "verbose", false,"Verbose output");
        options.addOption("h", "help", false,"Show help");

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(this.options, args);

        Bus bus = getBus();

        if (bus instanceof AtomicBus) {
            ((AtomicBus) bus).beginBusTransaction();
        }

        for(Command command: commands) {
            if (commandLine.hasOption(command.getIdentifier())) {
                CommandContext context = new CommandContext(this.output, commandLine.getOptionValue(command.getIdentifier()));
                bus.send(command, context);
            }
        }

        if (bus instanceof AtomicBus) {
            ((AtomicBus) bus).commitBusTransaction();
        }

        if (commandLine.getOptions().length < 1 || (commandLine.getOptions().length == 1 && commandLine.hasOption("help"))) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("klader-import", options);
        }
    }

}
