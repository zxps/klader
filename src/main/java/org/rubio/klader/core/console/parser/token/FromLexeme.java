package org.rubio.klader.core.console.parser.token;

import org.rubio.klader.core.console.parser.operator.Operator;

public class FromLexeme extends Lexeme {

    private String model;

    protected FromLexeme(Operator t) {
        super(t);
    }

    public String getModel(){
        return model;
    }

    @Override
    public void tokenize(String query) {
        String target = extract(query);
        model = target.trim().toLowerCase();
    }
}
