package com.webstore.exception;

import com.webstore.result.CodeMsg;


// self defined Exception with code message
public class GlobalException extends RuntimeException {
    private CodeMsg cm;
    private static final long serialVersionUID = 1L;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
