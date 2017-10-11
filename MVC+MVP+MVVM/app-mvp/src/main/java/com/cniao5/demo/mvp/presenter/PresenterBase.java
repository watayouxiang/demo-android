package com.cniao5.demo.mvp.presenter;

/**
 * Created by xzhang
 */

public interface PresenterBase<T> {

    void attachView(T v);

    void detachView();

}
