package com.webstore.result;

public class CodeMsg {
    private int code;
    private String msg;

    // General
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "SERVER_ERROR");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "Parameter Error：%s");
    // Login
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "SESSION_ERROR");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "PASSWORD_EMPTY");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "MOBILE_EMPTY");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "MOBILE_ERROR");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "MOBILE_NOT_EXIST");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "PASSWORD_ERROR");

    private CodeMsg (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
