package org.rubio.klader.core.console.parser.token;

import org.rubio.klader.core.console.parser.operator.Operator;

public class DescribeLexeme extends Lexeme{

    private String model;

    protected DescribeLexeme(Operator operator) {
        super(operator);
    }

    public String getModel() {
        return model;
    }

    @Override
    public void tokenize(String query) {
        model = extract(query);
    }
}
