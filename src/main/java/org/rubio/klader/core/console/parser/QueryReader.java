package org.rubio.klader.core.console.parser;

import org.rubio.klader.core.console.parser.operator.Operator;
import org.rubio.klader.core.console.parser.token.*;
import org.rubio.klader.core.console.parser.tree.TreeNode;

import java.lang.reflect.Field;
import java.util.*;

public class QueryReader {

    private final Integer DEFAULT_OFFSET = 0;
    private final Integer DEFAULT_LIMIT = 10;

    private Map<Operator, Lexeme> lexemes;
    private String query;

    public QueryReader(String query, Map<Operator, Lexeme> lexemes) {
        this.query = query;
        this.lexemes = lexemes;
    }

    public String getDescribeModel () {
        if (lexemes.containsKey(Operator.DESCRIBE)) {
            return ((DescribeLexeme) getLexeme(Operator.DESCRIBE)).getModel();
        }
        return null;
    }

    public TreeNode getWhereSyntaxTree() {
        if (lexemes.containsKey(Operator.WHERE)) {
            return ((WhereLexeme) getLexeme(Operator.WHERE)).getSyntaxTree();
        }
        return null;
    }

    public String getModel() {
        return ((FromLexeme) getLexeme(Operator.FROM)).getModel();
    }

    public List<String> getSelectFields(){
        return getSelectFields(null);
    }

    public List<String> getSelectFields(Object o) {
        List<String> selectFields = ((SelectLexeme) getLexeme(Operator.SELECT)).getFields();
        if (selectFields.contains("*") && o != null) {
            Field [] fields = o.getClass().getDeclaredFields();
            selectFields.remove("*");
            for(Field field: fields) {
                selectFields.add(field.getName());
            }
        }
        return selectFields;
    }

    public String getSelectDelimiter() {
        return ((SelectLexeme) getLexeme(Operator.SELECT)).getDelimiter();
    }

    public int getOffset() {
        try {
            return ((LimitLexeme) getLexeme(Operator.LIMIT)).getOffset();
        } catch (UnexpectedOperatorException e) {
            return DEFAULT_OFFSET;
        }
    }

    public int getLimit() {
        try {
            return ((LimitLexeme) getLexeme(Operator.LIMIT)).getLimit();
        } catch (UnexpectedOperatorException e ) {
            return DEFAULT_LIMIT;
        }
    }

    private Lexeme getLexeme(Operator token) {
        if (!lexemes.containsKey(token)) {
            throw new UnexpectedOperatorException();
        }
        return lexemes.get(token);
    }

    public Map<String, String> getSortMap() {
        try {
            return ((SortLexeme) getLexeme(Operator.ORDER_BY)).getMap();
        } catch (UnexpectedOperatorException e) {
            return Collections.emptyMap();
        }
    }

    public List<String> readFields(Object o) {
        List<String> selectFields = getSelectFields(o);
        List<String> buffer = new ArrayList<>();
        for(String fieldName: selectFields) {
            try {
                Field field = o.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(o);
                if (null != value) {
                    buffer.add(value.toString());
                } else {
                    buffer.add("NULL");
                }
            } catch (NoSuchFieldException e) {
                throw new UnexpectedModelFieldException("Field " + fieldName + " not allowed for model " + o.getClass().getName());
            } catch (IllegalAccessException e) {
                throw new UnexpectedModelFieldException("Access denied to field " + fieldName + " on model " + o.getClass().getName());
            }
        }
        return buffer;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Operator token: lexemes.keySet()) {
            builder.append(lexemes.get(token));
            builder.append(": ");
            builder.append(lexemes.get(token).extract(query));
        }
        return builder.toString();
    }

}
