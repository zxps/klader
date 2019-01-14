package org.rubio.klader.core.console.parser.token;

import org.rubio.klader.core.console.parser.operator.Operator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class SelectLexeme extends Lexeme {

    private List<String> fields;
    private String delimiter;

    SelectLexeme(Operator operator) {
        super(operator);
        delimiter = ",";
    }

    @Override
    public void tokenize(String query) {
        String target = extract(query);
        StringTokenizer t = new StringTokenizer(target, " \t\r\n,", false);
        List parts = Collections.list(t);
        fields = new ArrayList<>(parts);

        // @TODO Add delimiter parser/scanner
    }

    public List<String> getFields() {
        return fields;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
