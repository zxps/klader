package org.rubio.klader.core.repository;

import org.rubio.klader.core.model.Abbrev;
import org.rubio.klader.core.model.Code;
import org.rubio.klader.core.model.House;
import org.rubio.klader.core.model.Street;
import org.rubio.klader.core.storage.CharsetConfiguration;
import org.rubio.klader.core.storage.RegistryEntry;
import org.rubio.klader.core.storage.manager.RegistryManager;
import org.rubio.klader.core.storage.repository.ObjectRepository;

final public class HouseRepository extends ObjectRepository<House> {
    public HouseRepository(RegistryManager manager, CharsetConfiguration charsetConfiguration) {
        super(manager, charsetConfiguration);
    }

    @Override
    public RegistryEntry getEntry() {
        return this.getManager().getStorage().getRegistry().getHouses();
    }

    @Override
    protected House populate(Object[] row) {
        String name = translateToByteString(row[0]);
        String korp = translateToByteString(row[1]);
        Abbrev abbrev = new Abbrev(translateToByteString(row[2]));
        Code code = new Code(translateToByteString(row[3]));
        Integer index = translateToInteger(row[4]);
        Integer uno = translateToInteger(row[6]);

        return new House(name, korp, abbrev, code, index, uno);
    }

}
