package org.rubio.klader.core.console.parser;

public class UnexpectedOperatorException extends RuntimeException {

    public UnexpectedOperatorException() {
        super();
    }

    public UnexpectedOperatorException(String message) {
        super(message);
    }
}
