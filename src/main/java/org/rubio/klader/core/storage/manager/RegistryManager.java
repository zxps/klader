package org.rubio.klader.core.storage.manager;

import org.rubio.klader.core.config.Configurable;
import org.rubio.klader.core.repository.AbbrevRepository;
import org.rubio.klader.core.repository.HouseRepository;
import org.rubio.klader.core.storage.CharsetConfiguration;
import org.rubio.klader.core.storage.Storage;
import org.rubio.klader.core.storage.StorageException;
import org.rubio.klader.core.repository.CityRepository;
import org.rubio.klader.core.storage.repository.ObjectRepository;
import org.rubio.klader.core.repository.StreetRepository;
import org.rubio.klader.core.storage.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class RegistryManager implements Manager {

    private Storage storage;
    private Map<String, ObjectRepository> repositories;
    private Configurable config;

    public RegistryManager (Configurable config, Storage storage) {
        this.storage = storage;
        this.repositories = new HashMap<>();
        this.config = config;
    }

    public Storage getStorage() {
        return storage;
    }

    public Repository getRepository (String name) throws StorageException {
        String repositoryName = name.toLowerCase();
        if (repositories.containsKey(repositoryName)) {
            return repositories.get(repositoryName);
        }

        ObjectRepository repository;
        CharsetConfiguration charsetConfiguration
                = new CharsetConfiguration(config.getSourceCharset(), config.getOutputCharset());

        if (repositoryName.equals("city")) {
            repository = new CityRepository(this, charsetConfiguration);
        } else if (repositoryName.equals("street")) {
            repository = new StreetRepository(this, charsetConfiguration);
        } else if (repositoryName.equals("abbrev")) {
            repository = new AbbrevRepository(this, charsetConfiguration);
        } else if (repositoryName.equals("house")) {
            repository = new HouseRepository(this, charsetConfiguration);
        } else {
            throw new StorageException("ObjectRepository " + repositoryName + " not found");
        }

        repositories.put(repositoryName, repository);
        return repository;
    }
}
