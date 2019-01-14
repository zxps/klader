package org.rubio.klader.core.storage;

import org.apache.commons.io.FileUtils;
import org.rubio.klader.core.config.Configurable;
import org.rubio.klader.core.downloader.DownloaderAdapter;
import org.rubio.klader.core.unpacker.UnpackerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PrimaryStorage implements Storage {

    private Configurable config;
    private StorageRegistry registry;
    private DownloaderAdapter downloader;
    private UnpackerAdapter unpacker;

    public PrimaryStorage (Configurable configurable, DownloaderAdapter downloader, UnpackerAdapter unpacker) {
        this.config = configurable;
        this.downloader = downloader;
        this.unpacker = unpacker;
    }

    public StorageRegistry getRegistry() {
        return registry;
    }

    public void up () {
        if (null != registry) {
            return;
        }

        initFileSystem();

        List<File> files = Arrays.asList(new File(config.getUnpackedPath()).listFiles());
        registry = new PrimaryStorageRegistry(files);
    }

    public void renew(){
        try {
            invalidate();
            initFileSystem();
            String downloaded = download();
            unpack(downloaded);
            registry = null;
            up();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    public void invalidate() {
        File f = new File(this.config.getStoragePath());
        File [] files = f.listFiles();
        List<File> filesList = Arrays.asList(files != null ? files : new File [0]);
        for(File file: filesList) {
            if (file.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (file.isFile()) {
                FileUtils.deleteQuietly(file);
            }
        }
    }

    private void initFileSystem() {
        List<String> entries = this.config.getStorageEntries();
        for(String entry: entries) {
            File directory = new File(this.config.getStoragePath() + File.separator + entry);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
    }

    private String download () throws IOException{
        return downloader.download(config.getKladrArchive(), config.getTempPath());
    }

    private void unpack (String filename) throws IOException {
        unpacker.unpack(config.getTempPath() + File.separator + filename, config.getUnpackedPath());
    }
}
