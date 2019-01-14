package org.rubio.klader.core.console.parser;

import org.rubio.klader.core.console.parser.operator.Operator;
import org.rubio.klader.core.console.parser.tree.TreeNode;
import org.rubio.klader.core.console.parser.tree.Token;

import java.util.*;

public class ExpressionParser {

    private String expr;
    private Stack<Token> operatorStack;
    private Stack<TreeNode> outputStack;
    private TreeNode head;

    public ExpressionParser(String expression) {
        this.expr = expression;
    }

    public TreeNode parse() {
        head = null;
        operatorStack = new Stack<>();
        outputStack = new Stack<>();
        Map<String, Operator> operators = createOperatorsTable(Operator.expressionOperators());
        int pos = 0;
        while(pos < expr.length()) {
            TokenEntry entry = getToken(operators, pos);
            if (entry.getShift() > 0) {
                pos += entry.getShift();
            } else {
                pos ++;
            }

            if (entry.empty()) {
                //System.out.println("Skip:" + entry);
                continue;
            }

            //System.out.println("Current entry:" + entry);

            if (entry.getToken().isOperator()) {
                switch (entry.getToken().getOperator()) {
                    case BRACE_OPEN:
                        operatorStack.push(entry.getToken());
                        break;
                    case BRACE_CLOSE:
                        while (!operatorStack.isEmpty()) {
                            Token token = operatorStack.peek();
                            if (token.isOperator(Operator.BRACE_OPEN)) {
                                break;
                            }
                            TreeNode node = createSubtree(operatorStack.pop(), null);
                            outputStack.push(node);
                        }
                        operatorStack.pop();
                        break;
                    default:
                        if (!operatorStack.isEmpty() && !operatorStack.peek().isBrace()) {
                            if (!operatorStack.isEmpty() && entry.getToken().getOperator().isLessOrEqualPrecedenceTo(operatorStack.peek().getOperator())) {
                                TreeNode node = createSubtree(operatorStack.pop(), null);
                                outputStack.push(node);
                            }
                        }
                        operatorStack.push(entry.getToken());
                }
            } else {
                outputStack.push(new TreeNode(entry.getToken()));
            }
        }

        while (!operatorStack.isEmpty()) {
            updateTree();
        }

        if (null == head && outputStack.size() == 1) {
            head = outputStack.pop();
        }

        return head;
    }

    private void updateTree() {
        Token token = operatorStack.pop();
        TreeNode node = outputStack.pop();

        if (null == head) {
            head = new TreeNode(token);
            head.setLeft(outputStack.pop());
            head.setRight(node);
        } else {
            TreeNode subtree = head;
            head = new TreeNode(token);
            head.setLeft(node);
            head.setRight(subtree);
        }
    }

    private TreeNode createSubtree (Token token, TreeNode tree) {
        if (null == tree) {
            tree = new TreeNode(token);
            TreeNode right = outputStack.pop();
            TreeNode left = outputStack.pop();
            tree.setRight(right);
            tree.setLeft(left);
        } else {
            TreeNode subtree = tree;
            TreeNode left = outputStack.pop();
            tree = new TreeNode(token);
            tree.setRight(subtree);
            tree.setLeft(left);
        }
        if (!operatorStack.isEmpty() && !operatorStack.peek().isBrace()) {
            if (!operatorStack.isEmpty() && token.getOperator().isLessOrEqualPrecedenceTo(operatorStack.peek().getOperator())) {
                return createSubtree(operatorStack.pop(), tree);
            }
        }

        return tree;
    }

    private int searchToken(String subject, int exprPos) {
        int len = subject.length();
        int exprLen = expr.length();
        for (int operPos = 0; operPos < len && exprPos < exprLen; operPos++, exprPos++) {
            if (!eq(exprPos, subject, operPos)) {
                break;
            }
            if (operPos >= len - 1) {
                if (isBraceOperator(subject)) {
                    return (operPos + 1);
                } else if (isEmpty(exprPos + 1)){
                    return (operPos + 1);
                }
            }
        }
        return -1;
    }

    private boolean isBraceOperator(String subject) {
        return subject.length() == 1
                && (subject.charAt(0) == '(' || subject.charAt(0) == ')');
    }

    private boolean isEmpty(int pos) {
        return pos >= expr.length()
                || Character.isWhitespace(expr.charAt(pos))
                || expr.charAt(pos) == '(' // @TODO
                || expr.charAt(pos) == ')'; // @TODO
    }

    private Map<String, Operator> createOperatorsTable(Operator [] operators) {
        Map<String, Operator> table = new HashMap<>();
        for(Operator operator: operators) {
            table.put(operator.getValue(), operator);
        }
        return table;
    }

    private TokenEntry findNextOperator (Map<String, Operator> operators, int position) {
        int pos = position;
        int len = expr.length();
        int s = 0;
        List<String> operatorSubjects = new ArrayList<>(operators.keySet());
        Collections.sort(operatorSubjects, (s1, t1) -> Integer.compare(t1.length(), s1.length()));
        List<TokenEntry> results = new ArrayList<>();
        while (pos + s < len) {
            int p = pos + s;
            for(String subject: operatorSubjects) {
                int k = searchToken(subject, p);
                if (k >= 0) {
                    results.add(new TokenEntry(k + s, operators.get(subject)));
                    p = pos;
                }
            }
            s++;
        }
        if (results.size() < 1) {
            return null;
        }

        Collections.sort(results, Comparator.comparingInt(TokenEntry::getBegin));
        results = results.subList(0, 1);
        //System.out.println(results);
        //System.out.println("");
        //System.exit(1);
        return results.get(0);
    }

    private TokenEntry getToken(Map<String, Operator> operators, int position) {
        TokenEntry nextOperator = findNextOperator(operators, position);
        if (null == nextOperator) {
            String tokenValue = expr.substring(position, expr.length());
            TokenEntry entry = new TokenEntry(tokenValue.length(), tokenValue);
            return entry;
        }

        TokenEntry prevEntry = getPrevEntry(nextOperator, position);
        return prevEntry;
    }

    private TokenEntry getPrevEntry(TokenEntry entry, int position) {
        int shift = entry.getShift() - entry.length();
        if (shift == 0) {
            return entry;
        }
        String value = expr.substring(position, position + shift);
        return new TokenEntry(shift, value);
    }

    private boolean eq(int pos, String str, int strPos) {
        return Character.toUpperCase(expr.charAt(pos)) == Character.toUpperCase(str.charAt(strPos));
    }

    private class TokenEntry {

        private boolean found = false;
        private int shift = 0;
        private Token token;

        public TokenEntry() {
            found = false;
            shift = 0;
            token = null;
        }

        public TokenEntry(int shift, Object o) {
            this(shift, new Token(o));
        }

        public TokenEntry(int shift, Operator operator) {
            this(shift, new Token(operator));
        }

        public TokenEntry(int shift, Token token) {
            this.found = (shift > 0);
            this.shift = shift;
            this.token = token;
        }

        public int length () {
            return token.length();
        }

        public boolean empty() {
            if (this.token.isValue()) {
                String value = this.token.getValue().toString().trim();
                return value.length() < 1;
            }
            return false;
        }

        public Token getToken() {
            return token;
        }

        public int getShift() {
            return shift;
        }

        public int getBegin() {
            return shift - length();
        }

        public boolean isToken() {
            return found;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            builder.append(token.toString());
            builder.append(":");
            builder.append(getBegin());
            builder.append("-");
            builder.append(getShift());
            builder.append("}");
            return builder.toString();
        }
    }
}
