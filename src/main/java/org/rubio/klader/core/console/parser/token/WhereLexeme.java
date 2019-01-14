package org.rubio.klader.core.console.parser.token;

import org.rubio.klader.core.console.parser.ExpressionParser;
import org.rubio.klader.core.console.parser.operator.Operator;
import org.rubio.klader.core.console.parser.tree.TreeNode;
import org.rubio.klader.core.console.parser.tree.TreePrinter;

import java.util.*;

public class WhereLexeme extends Lexeme {

    private TreeNode tree;

    WhereLexeme(Operator operator) {
        super(operator);
    }

    public TreeNode getSyntaxTree() {
        return tree;
    }

    @Override
    public void tokenize(String query) {
        String target = extract(query);
        ExpressionParser parser = new ExpressionParser(target);
        tree = parser.parse();
        //TreePrinter.print(tree);
        //System.exit(1);
    }
}
