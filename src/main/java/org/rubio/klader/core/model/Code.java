package org.rubio.klader.core.model;

public class Code implements Comparable{

    private String value;

    public Code(String code) {
        this.value = code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Code) obj).getValue().equals(value);
    }

    @Override
    public int compareTo(Object o) {
        return value.compareTo(((Code) o).getValue());
    }
}
