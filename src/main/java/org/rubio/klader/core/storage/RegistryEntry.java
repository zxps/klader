package org.rubio.klader.core.storage;

import org.jamel.dbf.processor.DbfProcessor;
import org.rubio.klader.core.processor.MetaProcessor;

import java.io.File;

public class RegistryEntry
{
    private File file;

    public RegistryEntry(String filename) {
        file = new File(filename);
    }

    public File getFile() {
        return file;
    }

    public int countRows() {
        MetaProcessor metaProcessor = new MetaProcessor();
        DbfProcessor.processDbf(file, metaProcessor);
        return metaProcessor.getRows();
    }

    public String getInfo() {
        return DbfProcessor.readDbfInfo(file);
    }
}
