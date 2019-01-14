package org.rubio.klader.core.storage.repository;

import org.jamel.dbf.DbfReader;
import org.rubio.klader.core.storage.CharsetConfiguration;
import org.rubio.klader.core.storage.RegistryEntry;
import org.rubio.klader.core.storage.manager.RegistryManager;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.util.*;

abstract public class ObjectRepository<T> implements Repository {

    private RegistryManager manager;

    private CharsetConfiguration charsetConfiguration;

    private Integer count;

    public ObjectRepository(
            final RegistryManager manager,
            final CharsetConfiguration charsetConfiguration
    ) {
        this.manager = manager;
        this.charsetConfiguration = charsetConfiguration;
        this.count = null;
    }

    public RegistryManager getManager() {
        return manager;
    }

    abstract public RegistryEntry getEntry();

    public CharsetConfiguration getCharsetConfiguration() {
        return charsetConfiguration;
    }

    public Charset getEncoding () {
        return charsetConfiguration.getEncoding();
    }

    @Override
    public int getCount() {
        if (null != count) {
            return count;
        }
        DbfReader reader = new DbfReader(getEntry().getFile());
        count = reader.getRecordCount();

        return count;
    }

    @Override
    public Map<String, String> execute(Criteria criteria) {
        Map<String, String> result = new HashMap();
        DbfReader reader = createReader();
        Object [] row = reader.nextRecord();
        CriteriaMatcher matcher = criteria.getMatcher();
        int counter = 0;
        while (null != row) {
            T entity = populate(row);
            if (matcher.match(entity)) {
                counter++;
            }
            row = reader.nextRecord();
        }
        result.put("COUNT(*)", String.valueOf(counter));
        return result;
    }

    @Override
    public List<T> search(Criteria criteria) {
        List<T> result = new ArrayList<>();
        int count = getCount();
        if (criteria.getOffset() >= count) {
            return result;
        }
        DbfReader reader = createReader();
        Object [] row = reader.nextRecord();
        CriteriaMatcher matcher = criteria.getMatcher();
        int cursor = 0;

        while (null != row) {
            if (cursor >= criteria.getOffset()) {
                T entity = populate(row);
                if (matcher.match(entity)) {
                    result.add(entity);
                }
                if (result.size() >= criteria.getMaxResults()) {
                    break;
                }
            }
            cursor++;
            row = reader.nextRecord();
        }

        result.sort(new CriteriaSorter(criteria.getSort()));

        return result;
    }


    @Override
    public T find(Criteria criteria) {
        DbfReader reader = createReader();
        Object [] row = reader.nextRecord();
        while (null != row) {
            T a = populate(row);
            T query = (T) criteria.getQuery();
            if (a.equals(query)) {
                return a;
            }
            row = reader.nextRecord();
        }
        return null;
    }


    protected String translateToByteString(Object bytes) {
        return new String((byte[]) bytes, getEncoding());
    }

    protected Integer translateToInteger(Object bytes) {
        String value = translateToByteString(bytes).trim();
        if (value.length() > 0) {
            return Integer.valueOf(value);
        }
        return null;
    }

    protected DbfReader createReader() {
        return new DbfReader(getEntry().getFile());
    }

    abstract protected T populate(Object [] row);

    public Class getModelClass () {
        ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
        return (Class)type.getActualTypeArguments()[0];
    }

    public List<String> getModelFields() {
        List<String> modelFields = new ArrayList<>();
        Field [] fields = getModelClass().getDeclaredFields();
        for(Field field: fields) {
            modelFields.add(field.getName());
        }
        return modelFields;
    }
}
