package org.rubio.klader.core.console.parser.token;

import org.rubio.klader.core.console.parser.UnexpectedOperatorException;
import org.rubio.klader.core.console.parser.operator.Operator;

import java.util.*;

public class SortLexeme extends Lexeme {
    private Map<String, String> map;

    SortLexeme(Operator operator) {
        super(operator);
    }

    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public void tokenize(String query) {
        map = new HashMap<>();
        String target = extract(query);
        List groups = Collections.list(new StringTokenizer(target, ","));
        List<String> availDirections = Arrays.asList("ASC", "DESC");
        for(Object group: groups) {
            List rules = Collections.list(new StringTokenizer((String) group, " \t\r"));
            if (rules.size() < 1) {
                throw new UnexpectedOperatorException("Unexpected ORDER_BY expressionOperators: (" + target + ")");
            }
            String field = (String) rules.get(0);
            String direction = (rules.size() > 1 ? (String) rules.get(1) : "ASC").toUpperCase();

            if (!availDirections.contains(direction)) {
                direction = "ASC";
            }
            map.put(field, direction);
        }
    }
}
