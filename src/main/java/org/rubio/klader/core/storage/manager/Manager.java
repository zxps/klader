package org.rubio.klader.core.storage.manager;

import org.rubio.klader.core.storage.Storage;
import org.rubio.klader.core.storage.StorageException;
import org.rubio.klader.core.storage.repository.Repository;

public interface Manager {

    public Storage getStorage();

    public Repository getRepository(String name) throws StorageException;
}
