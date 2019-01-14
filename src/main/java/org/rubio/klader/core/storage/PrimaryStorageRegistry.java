package org.rubio.klader.core.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrimaryStorageRegistry implements StorageRegistry {

    private RegistryEntry main;
    private RegistryEntry altnames;
    private RegistryEntry houses;
    private RegistryEntry nameMap;
    private RegistryEntry abbrevs;
    private RegistryEntry streets;

    public PrimaryStorageRegistry(List<File> files) {
        for(File file: files) {
            if (file.getName().toLowerCase().equals("kladr.dbf")) {
                main = new RegistryEntry(file.getAbsolutePath());
            } else if (file.getName().toLowerCase().equals("street.dbf")) {
                streets = new RegistryEntry(file.getAbsolutePath());
            } else if (file.getName().toLowerCase().equals("doma.dbf")) {
                houses = new RegistryEntry(file.getAbsolutePath());
            } else if (file.getName().toLowerCase().equals("socrbase.dbf")) {
                abbrevs = new RegistryEntry(file.getAbsolutePath());
            } else if (file.getName().toLowerCase().equals("altnames.dbf")) {
                altnames = new RegistryEntry(file.getAbsolutePath());
            } else if (file.getName().toLowerCase().equals("namemap.dbf")) {
                nameMap = new RegistryEntry(file.getAbsolutePath());
            }
        }
    }

    @Override
    public RegistryEntry getCity() {
        return main;
    }

    @Override
    public RegistryEntry getAltnames() {
        return altnames;
    }

    @Override
    public RegistryEntry getHouses() {
        return houses;
    }

    @Override
    public RegistryEntry getNameMap() {
        return nameMap;
    }

    @Override
    public RegistryEntry getAbbrevs() {
        return abbrevs;
    }

    @Override
    public RegistryEntry getStreets() {
        return streets;
    }

    public List<RegistryEntry> asList() {
        List<RegistryEntry> list = new ArrayList<>();
        list.add(main);
        list.add(streets);
        list.add(houses);
        list.add(abbrevs);
        list.add(altnames);
        list.add(nameMap);
        return list;
    }

    @Override
    public Iterator<RegistryEntry> iterator() {
        return asList().iterator();
    }
}
