package org.rubio.klader.core.storage.repository;

import java.util.List;
import java.util.Map;

public interface Repository <T> {

    public Map<String, String> execute(Criteria criteria);

    public List<T> search(Criteria criteria);

    public T find(Criteria criteria);

    public int getCount();

    public Class getModelClass();

    public List<String> getModelFields();
}
