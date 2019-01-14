package org.rubio.klader.core.console.parser;

public class UnexpectedModelFieldException extends RuntimeException {

    public UnexpectedModelFieldException() {
        super();
    }

    public UnexpectedModelFieldException(String message) {
        super(message);
    }
}
