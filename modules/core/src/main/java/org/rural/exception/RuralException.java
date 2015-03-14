package org.rural.exception;

/**
 * Created by yuantao on 2014/8/9.
 */
public class RuralException extends RuntimeException {
    public RuralException(Exception e){
        super(e);
    }

    public RuralException(String message) {
        super(message);
    }

    public RuralException() {
        super("Rural Exception");
    }

}
