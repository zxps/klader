package org.rubio.klader.core.storage;

public interface StorageRegistry extends Iterable<RegistryEntry> {

    public RegistryEntry getCity();

    public RegistryEntry getAltnames();

    public RegistryEntry getHouses();

    public RegistryEntry getNameMap();

    public RegistryEntry getAbbrevs();

    public RegistryEntry getStreets();
}
