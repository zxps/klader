package org.rubio.klader.core.console.parser.token;

import org.rubio.klader.core.console.parser.UnexpectedOperatorException;
import org.rubio.klader.core.console.parser.operator.Operator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class LimitLexeme extends Lexeme {

    private int offset;
    private int limit;

    LimitLexeme(Operator operator) {
        super(operator);
        offset = 0;
        limit = 10;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public void tokenize(String query) {
        String target = extract(query);
        StringTokenizer tokenizer = new StringTokenizer(target, " \t\n\r\f,", false);
        ArrayList parts = Collections.list(tokenizer);
        if (parts.size() > 2) {
            throw new UnexpectedOperatorException(getOperator().name() + " token error. ([ OFFSET [,LIMIT] ] allowed)");
        }

        if (2 == parts.size()) {
            offset = Integer.valueOf((String) parts.get(0));
            limit = Integer.valueOf((String) parts.get(1));
        } else if (1 == parts.size()) {
            limit = Integer.valueOf((String) parts.get(0));
        }
    }

}
