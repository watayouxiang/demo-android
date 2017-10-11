package com.cniao5.demo.mvp.presenter;


import com.cniao5.demo.mvp.model.User;
import com.cniao5.demo.mvp.view.ViewBaseMain;

/**
 * 只为MainActivity提供业务逻辑
 * Created by xzhang
 */

public interface PresenterBaseMain extends PresenterBase<ViewBaseMain> {

    void login(User user);

}
