package org.rubio.klader.core.model;

public class City {

    private String name;
    private Abbrev abbrev;
    private Code code;
    private Short status;

    public City(String name) {
        this.name = name;
    }

    public City(String name, Abbrev abbrev, Code code, Short status) {
        this.name = name;
        this.abbrev = abbrev;
        this.code = code;
        this.status = status;
    }

    public Short getStatus() {
        return this.status;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) { this.name = name; }

    public Abbrev getAbbrev() {
        return this.abbrev;
    }
    public void setAbbrev(Abbrev abbrev) { this.abbrev = abbrev; }

    public Code getCode() {
        return this.code;
    }
    public void setCode(Code code) { this.code = code; }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        City city = (City) obj;
        if (city.getName().equals(name)) {
            return true;
        }

        return city.getCode().equals(code);
    }
}
