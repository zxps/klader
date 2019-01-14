package org.rubio.klader.core.config;

import java.util.List;

public interface Configurable {

    public String getKladrArchive();

    public String getStoragePath();

    public List<String> getStorageEntries();

    public String getTempPath();

    public String getUnpackedPath();

    public String getSourceCharset();

    public String getOutputCharset();
}
