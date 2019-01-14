package org.rubio.klader.core.console.parser.tree;

import org.rubio.klader.core.console.parser.operator.Operator;

public class TreeNode implements TreePrinter.PrintableNode {

    private Token token;
    private TreeNode parent;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(Token token, TreeNode parent, TreeNode left, TreeNode right) {
        this.token = token;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public TreeNode(Token token) {
        this(token, null, null, null);
    }

    public TreeNode() {
        this(null, null, null, null);
    }

    public TreeNode getParent() {
        return parent;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    @Override
    public String getText() {
        return this.token.toString();
    }

    public void setParent(TreeNode node) {
        parent = node;
    }

    public void setRight(TreeNode node) {
        right = node;
    }

    public void setLeft(TreeNode node) {
        left = node;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isRoot() {
        return (parent == null);
    }

    public boolean isLeaf() {
        return (right == null && left == null);
    }

    public boolean isOperator(Operator o) {
        if (null != token) {
            return token.isOperator(o);
        }
        return false;
    }

    public boolean isValue() {
        if (null != token) {
            return token.isValue();
        }
        return false;
    }

    public void removeParent() {
        parent = null;
    }
}
