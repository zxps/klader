package org.rubio.klader.core.storage.repository;

import java.util.HashMap;
import java.util.Map;

public class Criteria {
    private int maxResults;
    private int offset;
    private Object query;
    private Map<String, String> sort;
    private CriteriaMatcher matcher;

    public Criteria() {
        this(o -> true);
    }

    public Criteria(CriteriaMatcher matcher){
        this.maxResults = 10;
        this.offset = 0;
        this.query = null;
        this.sort = new HashMap<>();
        this.matcher = matcher;
    }

    public static Criteria forQuery(Object object) {
        Criteria criteria = new Criteria();
        criteria.setQuery(object);
        return criteria;
    }

    public CriteriaMatcher getMatcher() {
        return matcher;
    }

    public void setMatcher(CriteriaMatcher matcher) {
        this.matcher = matcher;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setSort(Map<String, String> sort) {
        this.sort = sort;
    }

    public Map<String, String> getSort() {
        return sort;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Object getQuery() {
        return query;
    }

    public void setQuery(Object query) {
        this.query = query;
    }
}
