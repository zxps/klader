package org.rubio.klader.core.storage.repository.matcher;

import org.rubio.klader.core.console.parser.UnexpectedOperatorException;
import org.rubio.klader.core.console.parser.operator.Operator;
import org.rubio.klader.core.console.parser.tree.TreeNode;
import org.rubio.klader.core.storage.repository.CriteriaMatcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimaryCriteriaMatcher implements CriteriaMatcher {

    private TreeNode syntaxTree;

    public PrimaryCriteriaMatcher (TreeNode tree) {
        syntaxTree = tree;
    }

    @Override
    public boolean match(Object o) {
        boolean matched = true;
        return matched;
    }

    /*
    @Override
    public boolean match(Object o) {
        boolean matched = true;
        for(Expression expression: expressions) {
            String fieldName = ((String) expression.getParts().get(0)).trim();
            Field field;
            try {
                field = o.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                throw new UnexpectedOperatorException("Unexpected field " + fieldName + " in expressionOperators");
            }
            field.setAccessible(true);
            try {
                Object value = field.get(o);
                String toCompare = (String) expression.getParts().get(1);

                if (expression.isOperator(Operator.MATCH)) {
                    Pattern regexp = Pattern.compile(toCompare.trim());
                    Matcher m = regexp.matcher(value.toString().trim());
                    matched = matched && m.matches();
                } else if (expression.isOperator(Operator.EQ)) {
                    matched = matched && toCompare.trim().equals(value.toString().trim());
                } else if (expression.isOperator(Operator.NOT_EQ)) {
                    matched = matched && !(toCompare.trim().equals(value.toString().trim()));
                } else if (expression.isOperator(Operator.GREATER)) {
                    matched = Integer.valueOf(value.toString().trim()) > Integer.valueOf(toCompare.trim());
                } else if (expression.isOperator(Operator.LESS)) {
                    matched = Integer.valueOf(value.toString().trim()) < Integer.valueOf(toCompare.trim());
                }

                if (!matched) {
                    break;
                }

            } catch (IllegalAccessException e) {
                throw new UnexpectedOperatorException("Access denied to field " + field.getName() + " in model " + o.getClass().getName());
            }

        }

        return matched;
    }
    */
}
