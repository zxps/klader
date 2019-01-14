package org.rubio.klader.core.model;

public class Street {

    private Abbrev abbrev;
    private String name;
    private Code code;
    private Integer index;
    private Integer uno;

    public Street(String name, Abbrev abbrev, Code code, Integer index, Integer uno) {
        this.name = name;
        this.abbrev = abbrev;
        this.code = code;
        this.index = index;
        this.uno = uno;
    }

    public String getName() {
        return name;
    }

    public Abbrev getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(Abbrev abbrev) {
        this.abbrev = abbrev;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Code getCode() {
        return code;
    }
    public void setCode(Code code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }


    @Override
    public boolean equals(Object obj) {
        Street street = (Street) obj;
        if (street.getName().equals(name)) {
            return true;
        }

        return street.getCode().equals(code);
    }
}
