package com.cniao5.demo.mvp.view;

/**
 * 只负责MainActivty中的UI逻辑
 *
 * Created by xzhang on
 */

public interface ViewBaseMain extends ViewBase {
    void loginSuccess(String msg) ;
    void loginFailed(String msg) ;
}
