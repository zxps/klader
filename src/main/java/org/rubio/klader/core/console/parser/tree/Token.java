package org.rubio.klader.core.console.parser.tree;

import org.rubio.klader.core.console.parser.operator.Operator;

public class Token {
    private Operator operator;
    private Object value;

    public Token (Operator operator) {
        this.value = null;
        this.operator = operator;
    }

    public Token(Object value) {
        this.value = value;
        this.operator = null;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public boolean isOperator () {
        return operator != null;
    }

    public boolean isBrace() {
        if (isOperator()) {
            return operator.isBrace();
        }
        return false;
    }

    public boolean isOperator(Operator o){
        if (isOperator()) {
            return operator.equals(o);
        }
        return false;
    }

    public boolean isValue() {
        return value != null;
    }

    public int length () {
        if (isOperator()){
            return operator.getValue().length();
        }
        return value.toString().length();
    }

    public String toString () {
        StringBuilder builder = new StringBuilder();
        if (null != operator) {
            builder.append(operator.getValue());
        }
        if (null != value) {
            builder.append(value.toString());
        }
        return builder.toString();
    }
}
