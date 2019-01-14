package org.rubio.klader.core.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config extends Properties implements Configurable {

    public Config() {
        InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties");
        if (null == input) {
            System.out.println("Unable to file application.properties");
        }
         try {
            this.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getKladrArchive() {
        return this.getProperty("kladr.archive");
    }

    @Override
    public String getTempPath() {
        return this.getStoragePath() + File.separator + "tmp";
    }

    @Override
    public String getUnpackedPath() {
        return this.getStoragePath() + File.separator + "unpacked";
    }

    @Override
    public String getSourceCharset() {
        return this.getProperty("klader.charset.source");
    }

    @Override
    public String getOutputCharset() {
        return this.getProperty("klader.charset.output");
    }

    @Override
    public String getStoragePath() {
        return this.getProperty("klader.storage.path");
    }

    @Override
    public List<String> getStorageEntries() {
        return new ArrayList<String>(
                Arrays.asList(
                        this.getProperty("klader.storage.entries").split(",")
                )
        );
    }
}
