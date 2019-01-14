package org.rubio.klader.core.model;


public class House implements Comparable{

    private City city;
    private Street street;
    private Abbrev abbrev;
    private String name;
    private String korp;
    private Code code;
    private Integer index;
    private Integer uno;

    public House(String name, String korp, Abbrev abbrev, Code code, Integer index, Integer uno) {
        this(name, korp, abbrev, code, index, uno, null, null);
    }

    public House(String name, String korp, Abbrev abbrev, Code code, Integer index, Integer uno, City city, Street street) {
        this.name = name;
        this.korp = korp;
        this.abbrev = abbrev;
        this.code = code;
        this.index = index;
        this.uno = uno;
        this.city = city;
        this.street = street;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((House) o).getName());
    }
}
