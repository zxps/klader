package org.rubio.klader.core.command.types;

import java.io.OutputStream;

public class CommandContext {
    private String options;
    private OutputStream output;

    public CommandContext(OutputStream output, String options) {
        this.output = output;
        this.options = options;
    }

    public OutputStream getOutput () {
        return output;
    }

    public String getOptions() {
        return options;
    }
}
