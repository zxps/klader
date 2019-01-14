package org.rubio.klader.core.downloader;

import java.io.IOException;

public interface DownloaderAdapter {

    public String download(String file, String path) throws IOException;

}
