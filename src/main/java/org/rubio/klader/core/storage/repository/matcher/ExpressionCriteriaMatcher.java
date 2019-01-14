package org.rubio.klader.core.storage.repository.matcher;

import org.rubio.klader.core.console.parser.UnexpectedOperatorException;
import org.rubio.klader.core.console.parser.operator.Operator;
import org.rubio.klader.core.console.parser.tree.TreeNode;
import org.rubio.klader.core.storage.repository.CriteriaMatcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionCriteriaMatcher implements CriteriaMatcher {

    private TreeNode tree;

    public ExpressionCriteriaMatcher(TreeNode tree) {
        this.tree = tree;
    }

    @Override
    public boolean match(Object o) {
        return eval(tree, o);
    }

    private boolean eval(TreeNode node, Object o) {
        boolean result = true;

        TreeNode left = node.getLeft();
        TreeNode right = node.getRight();
        Object value1, value2;
        Operator operator = node.getToken().getOperator();
        switch(operator){
            case AND:
            case AND2:
                return eval(left, o) && eval(right, o);
            case OR:
            case OR2:
                return eval(left, o) || eval(right, o);
            case GREATER:
            case GREATER_EQ:
            case LESS:
            case LESS_EQ:
                value1 = getObjectValue(o, left.getToken().getValue().toString().trim());
                value2 = right.getToken().getValue();
                Integer intValue1 = Integer.valueOf(value1.toString().trim());
                Integer intValue2 = Integer.valueOf(value2.toString().trim());
                switch (operator){
                    case GREATER:
                        return intValue1 > intValue2;
                    case GREATER_EQ:
                        return intValue1 >= intValue2;
                    case LESS:
                        return intValue1 < intValue2;
                    case LESS_EQ:
                        return intValue1 <= intValue2;
                }
            case MATCH:
            case NOT_MATCH:
                value1 = getObjectValue(o, left.getToken().getValue().toString().trim());
                if (null == value1) {
                    return false;
                }
                value2 = right.getToken().getValue();
                Pattern regexp = Pattern.compile(value2.toString().trim());
                Matcher m = regexp.matcher(value1.toString().trim());
                boolean matches = m.matches();
                return operator.equals(Operator.MATCH) ? matches : !matches;
            case EQ:
            case NOT_EQ:
                value1 = getObjectValue(o, left.getToken().getValue().toString().trim());
                value2 = right.getToken().getValue();
                boolean eq = value1.toString().equals(value2.toString());
                return operator.equals(Operator.EQ) ? eq : !eq;
            case IN:
            case NOT_IN:
                value1 = getObjectValue(o, left.getToken().getValue().toString().trim());
                List objects = Collections.list(new StringTokenizer(right.getToken().getValue().toString(), ","));
                objects = new ArrayList<String>(objects);
                boolean in = objects.contains(value1.toString());
                return operator.equals(Operator.IN) ? in : !in;
        }

        return result;
    }

    private Object getObjectValue(Object o, String fieldName) {
        Field field;
        try {
            field = o.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new UnexpectedOperatorException("Unexpected field " + fieldName + " in WHERE clause");
        }
        field.setAccessible(true);
        try {
            return field.get(o);
        } catch (IllegalAccessException e) {
            throw new UnexpectedOperatorException("Access denied to field " + field.getName() + " in model " + o.getClass().getName());
        }
    }
}
