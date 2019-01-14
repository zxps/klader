package org.rubio.klader.core.console.parser.operator;

public enum Operator {

    SELECT("SELECT"),
    FROM("FROM"),
    WHERE("WHERE"),
    LIMIT("LIMIT"),
    ORDER_BY("ORDER BY"),
    OPTION("OPTION"),
    DESCRIBE("DESCRIBE"),

    BRACE_OPEN(2, "("),
    BRACE_CLOSE(1, ")"),

    NULL("NULL"),
    NOT_NULL("NOT NULL"),
    EMPTY("EMPTY"),

    AND(2,"&&"),
    AND2(2, "AND"),
    OR(2, "||"),
    OR2(2, "OR"),
    IS(10, "IS"),
    IN(10, "IN"),
    NOT_IN(10, "!IN"),
    NOT_EQ(10, "!="),
    MATCH(10, "MATCH"),
    NOT_MATCH(10, "!MATCH"),
    EQ(10, "="),
    LESS(10, "<"),
    LESS_EQ(10, "<="),
    GREATER(10, ">"),
    GREATER_EQ(10, ">=");

    private int priority;
    private String subject;

    Operator(String s) {
        this(0, s);
    }
    Operator(int priority, String s) {
        this.subject = s;
        this.priority = priority;
    }

    public boolean isBrace() {
        return this.equals(BRACE_CLOSE) || this.equals(BRACE_OPEN);
    }

    public boolean isLessOrEqualPrecedenceTo(Operator operator) {
        return this.priority <= operator.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public String getValue() {
        return this.subject;
    }

    public static Operator[] expressionOperators() {
        return new Operator[] {
                BRACE_OPEN, BRACE_CLOSE,
                AND, AND2, OR, OR2,
                GREATER, GREATER_EQ, LESS, LESS_EQ,
                EQ, NOT_EQ,
                IN, NOT_IN, MATCH, NOT_MATCH, IS
        };
    }

    public static Operator[] primaryOperators() {
        return new Operator[] {
                SELECT,FROM,WHERE,ORDER_BY,LIMIT,OPTION,DESCRIBE
        };
    }
}
