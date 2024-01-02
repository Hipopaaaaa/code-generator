package com.ohj.maker.meta;

/**
 * @author Hipopaaaaa
 * @create 2024/1/2 19:35
 * 元信息异常
 */
public class MetaException extends RuntimeException{
    public MetaException() {
    }

    public MetaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetaException(String message) {
        super(message);
    }
}
