package org.rubio.klader.core.repository;

import org.rubio.klader.core.model.Abbrev;
import org.rubio.klader.core.storage.CharsetConfiguration;
import org.rubio.klader.core.storage.RegistryEntry;
import org.rubio.klader.core.storage.manager.RegistryManager;
import org.rubio.klader.core.storage.repository.ObjectRepository;

public class AbbrevRepository extends ObjectRepository<Abbrev> {

    public AbbrevRepository(RegistryManager manager, CharsetConfiguration charsetConfiguration) {
        super(manager, charsetConfiguration);
    }

    @Override
    public RegistryEntry getEntry() {
        return getManager().getStorage().getRegistry().getAbbrevs();
    }

    @Override
    protected Abbrev populate(Object [] row) {
        Integer level = translateToInteger(row[0]);
        String label = translateToByteString(row[1]);
        String name = translateToByteString(row[2]);
        Integer code = translateToInteger(row[3]);

        return new Abbrev(level, code, name, label);
    }
}
