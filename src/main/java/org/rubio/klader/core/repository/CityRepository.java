package org.rubio.klader.core.repository;

import org.rubio.klader.core.model.Abbrev;
import org.rubio.klader.core.model.City;
import org.rubio.klader.core.model.Code;
import org.rubio.klader.core.storage.CharsetConfiguration;
import org.rubio.klader.core.storage.RegistryEntry;
import org.rubio.klader.core.storage.manager.RegistryManager;
import org.rubio.klader.core.storage.repository.ObjectRepository;


final public class CityRepository extends ObjectRepository<City>
{
    public CityRepository(RegistryManager manager, CharsetConfiguration charsetConfiguration) {
        super(manager, charsetConfiguration);
    }

    @Override
    public RegistryEntry getEntry() {
        return getManager().getStorage().getRegistry().getCity();
    }

    @Override
    protected City populate(Object [] row) {
        String name = translateToByteString(row[0]);
        Abbrev abbrev = new Abbrev(translateToByteString(row[1]));
        String code = translateToByteString(row[2]);
        Short status = Short.valueOf(translateToByteString(row[7]));
        return new City(name, abbrev, new Code(code), status);
    }
}
