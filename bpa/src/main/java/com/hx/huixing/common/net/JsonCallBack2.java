package com.hx.huixing.common.net;

/**
 * @Description:
 * @Author: Simon
 * @Created: 2017/3/27 18:14
 */

public interface JsonCallBack2<T> {

    void next(T data);

    void error(Throwable e);

    void startLoading();

    void closeLoading();

}
