package org.rubio.klader.core.console.parser;

import org.rubio.klader.core.console.parser.operator.Operator;
import org.rubio.klader.core.console.parser.token.Lexeme;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class QueryParser
{
    private String query;
    private LinkedList<Lexeme> lexemes;

    public QueryParser(String query){
        this.query = query;
    }

    public QueryReader parse() {
        lexemes = new LinkedList<>();
        Operator[] primaryOperators = Operator.primaryOperators();
        for (int pos = 0, size = query.length(); pos < size; pos++) {
            for(Operator operator: primaryOperators) {
                pos = tokenize(operator, pos, size);
            }
        }
        optimize();
        Map<Operator, Lexeme> mappedLexemes = new HashMap<>(lexemes.size());
        for(Lexeme lexeme: lexemes) {
            lexeme.tokenize(query);
            mappedLexemes.put(lexeme.getOperator(), lexeme);
        }

        return new QueryReader(query, mappedLexemes);
    }

    private void optimize() {
        if (lexemes.size() > 0) {
            if (lexemes.getLast().getEnd() < 0){
                lexemes.getLast().setEnd(query.length());
            }
        }
    }

    private int tokenize(Operator operator, int pos, int size) {
        for(int k = 0, n = pos, len = operator.getValue().length(); k < len && n < size; k++, n++) {
            if (!charsEqual(operator.getValue(), query, k, n)) {
                break;
            }
            if (k >= len - 1) {
                if (lexemes.size() > 0) {
                    lexemes.getLast().setEnd(pos-1);
                }
                Lexeme lexeme = Lexeme.getLexeme(operator);
                lexeme.setBegin(pos+len+1);
                lexemes.add(lexeme);
                pos += len;
            }
        }

        return pos;
    }

    private boolean charsEqual(String str1, String str2, int pos1, int pos2) {
        return Character.toUpperCase(str1.charAt(pos1)) == Character.toUpperCase(str2.charAt(pos2));
    }

}
