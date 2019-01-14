package org.rubio.klader.core.storage.repository;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Map;

public class CriteriaSorter implements Comparator {

    private Map<String, String> map;

    public CriteriaSorter(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public int compare(Object o, Object t1) {
        for(String key: map.keySet()) {
            String direction = map.get(key);

            Field field1 = getField(key, o);
            Field field2 = getField(key, t1);

            Object value1 = getValue(field1, o);
            Object value2 = getValue(field2, t1);

            int value = ((Comparable) value1).compareTo(value2);
            if (value != 0) {
                if (direction.equals("DESC")) {
                    value = (value * -1);
                }
                return value;
            }

        }
        return 0;
    }

    private Object getValue(Field field, Object object) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Access denied to sort field " + field.getName());
        }
    }

    private Field getField(String key, Object object) {
        Field field = null;
        try {
            field = object.getClass().getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Unexpected sort field " + key);
        }
        field.setAccessible(true);
        return field;
    }
}
