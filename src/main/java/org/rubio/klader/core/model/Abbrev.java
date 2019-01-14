package org.rubio.klader.core.model;

public class Abbrev implements Comparable{

    private Integer level;
    private Integer code;
    private String name;
    private String label;

    public Abbrev (String label) {
        this.label = label;
        this.name = null;
        this.code = null;
        this.level = null;
    }

    public Abbrev (Integer level, Integer code, String name, String label) {
        this.level = level;
        this.code = code;
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public String getLabel() {
        return label;
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) { this.code = code; }

    public Integer getLevel () {
        return level;
    }
    public void setLevel(Integer level) { this.level = level; }

    @Override
    public boolean equals(Object obj) {
        Abbrev a = (Abbrev) obj;
        if (getCode().equals(a.getCode())) {
            return true;
        }
        if (getLabel().equals(a.getLabel())) {
            return true;
        }
        return getName().equals(a.getName());
    }

    @Override
    public String toString() {
        return getLabel();
    }

    @Override
    public int compareTo(Object o) {
        return label.compareTo(((Abbrev) o).getLabel());
    }
}
