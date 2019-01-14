package org.rubio.klader.core.unpacker;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ArchiveUnpacker implements UnpackerAdapter
{
    public void unpack(String file, String path) throws IOException {
        byte [] buffer = new byte [1024];
        SevenZFile archiveFile = new SevenZFile(new File(file));
        SevenZArchiveEntry entry = archiveFile.getNextEntry();
        while(null != entry) {
            if (entry.isDirectory()){
                entry = archiveFile.getNextEntry();
                continue;
            }

            File newFile = new File(path, entry.getName());
            FileOutputStream fos = new FileOutputStream(newFile);
            int len = archiveFile.read(buffer);
            while (len > 0) {
                fos.write(buffer, 0, len);
                len = archiveFile.read(buffer);
            }
            fos.close();
            entry = archiveFile.getNextEntry();
        }
        archiveFile.close();
    }
}
