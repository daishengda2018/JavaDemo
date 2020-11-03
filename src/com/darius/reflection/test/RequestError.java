package com.darius.reflection.test;

/**
 * Create by im_dsd 2020/10/8 6:19 下午
 */
public class RequestError {
    public int stCode = 0;
    public String reason = "";
    public String raw = "";

    public RequestError() {
    }

    public RequestError(int code, String msg) {
        stCode = code;
        reason = msg;
    }

    public static RequestError create() {
        return new RequestError();
    }

    public static RequestError create(int code, String msg) {
        return new RequestError(code, msg);
    }

    @Override
    public String toString() {
        return "RequestError{" +
                "stCode=" + stCode +
                ", reason='" + reason + '\'' +
                ", raw='" + raw + '\'' +
                '}';
    }
}
