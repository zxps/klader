package org.rubio.klader.core.downloader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HttpDownloader implements DownloaderAdapter {

    public String download(String file, String path) throws IOException {
        URL url = new URL(file);
        String [] parts = url.getFile().split("/");
        String filename = parts[parts.length - 1];
        FileUtils.copyURLToFile(url, new File(path + File.separator + filename));
        return filename;
    }
}
