package org.rubio.klader.core.storage;

public interface Storage {

    public StorageRegistry getRegistry();

    public void up();

    public void renew();

    public void invalidate();
}
