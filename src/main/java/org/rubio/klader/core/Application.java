package org.rubio.klader.core;

import org.rubio.klader.core.config.Config;
import org.rubio.klader.core.config.Configurable;
import org.rubio.klader.core.command.types.Bus;
import org.rubio.klader.core.command.types.CommandBus;
import org.rubio.klader.core.downloader.HttpDownloader;
import org.rubio.klader.core.storage.PrimaryStorage;
import org.rubio.klader.core.storage.Storage;
import org.rubio.klader.core.storage.manager.RegistryManager;
import org.rubio.klader.core.unpacker.ArchiveUnpacker;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    RegistryManager manager;
    Bus bus;
    static Logger logger = Logger.getLogger(Application.class.getName());

    public Application () {
        Configurable config = new Config();
        Storage storage = new PrimaryStorage(config, new HttpDownloader(), new ArchiveUnpacker());
        manager = new RegistryManager(config, storage);
        storage.up();
        bus = createBus();
    }

    protected Bus createBus() {
        return new CommandBus(manager);
    }

    public Bus getBus() {
        return bus;
    }

    public static void log (String message) {
        logger.log(Level.INFO, message);
    }
}
