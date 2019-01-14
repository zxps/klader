package org.rubio.klader.core.console.parser.token;

import org.rubio.klader.core.console.parser.UnexpectedOperatorException;
import org.rubio.klader.core.console.parser.operator.Operator;

abstract public class Lexeme {

    private int begin;
    private int end;
    private Operator operator;

    Lexeme(Operator operator) {
        begin = -1;
        end = -1;
        this.operator = operator;
    }

    public static Lexeme getLexeme(Operator operator) {
        switch(operator) {
            case SELECT:
                return new SelectLexeme(operator);
            case FROM:
                return new FromLexeme(operator);
            case WHERE:
                return new WhereLexeme(operator);
            case ORDER_BY:
                return new SortLexeme(operator);
            case LIMIT:
                return new LimitLexeme(operator);
            case DESCRIBE:
                return new DescribeLexeme(operator);
            default:
                throw new UnexpectedOperatorException();
        }
    }

    abstract public void tokenize(final String query);

    @Override
    public String toString () {
        return operator.name() + "{" + begin + "," + end + "}";
    }

    public String extract(final String q) {
        return q.substring(this.begin, this.end);
    }

    public Operator getOperator() {
        return operator;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }
}
