package org.rubio.klader.core.storage;

import java.nio.charset.Charset;

public class CharsetConfiguration {

    private Charset encoding;
    private Charset output;

    public CharsetConfiguration(String encoding) {
        this.encoding = Charset.forName(encoding);
        this.output = null;
    }

    public CharsetConfiguration(String encoding, String output) {
        this.encoding = Charset.forName(encoding);
        this.output = Charset.forName(output);
    }

    public boolean convertible () {
        return null != this.output;
    }

    public Charset getEncoding() {
        return this.encoding;
    }

    public Charset getOutput() {
        return this.output;
    }
}
