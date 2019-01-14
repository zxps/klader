package org.rubio.klader.core.unpacker;

import java.io.IOException;

public interface UnpackerAdapter {

    public void unpack(String file, String path) throws IOException;

}
