package com.hx.huixing.common.http;

/**
 * created by tanjun
 * 请求网络数据（非list）
 * @param <T>
 */
public class Result<T> {
    private int code;
    private String msg;
    private T datas;

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

    public T getData() {
        return datas;
    }

    public void setData(T data) {
        this.datas = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + datas +
                '}';
    }
}
