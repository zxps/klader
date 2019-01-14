package org.rubio.klader.core.repository;

import org.rubio.klader.core.model.Abbrev;
import org.rubio.klader.core.model.Code;
import org.rubio.klader.core.model.Street;
import org.rubio.klader.core.storage.CharsetConfiguration;
import org.rubio.klader.core.storage.RegistryEntry;
import org.rubio.klader.core.storage.manager.RegistryManager;
import org.rubio.klader.core.storage.repository.ObjectRepository;

final public class StreetRepository extends ObjectRepository<Street> {
    public StreetRepository(RegistryManager manager, CharsetConfiguration charsetConfiguration) {
        super(manager, charsetConfiguration);
    }

    @Override
    public RegistryEntry getEntry() {
        return this.getManager().getStorage().getRegistry().getStreets();
    }

    @Override
    protected Street populate(Object[] row) {
        String name = translateToByteString(row[0]);
        Abbrev abbrev = new Abbrev(translateToByteString(row[1]));
        Code code = new Code(translateToByteString(row[2]));
        Integer index = translateToInteger(row[3]);
        Integer uno = translateToInteger(row[5]);

        return new Street(name, abbrev, code, index, uno);
    }

}
