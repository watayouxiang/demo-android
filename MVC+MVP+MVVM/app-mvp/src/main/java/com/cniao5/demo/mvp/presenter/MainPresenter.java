package com.cniao5.demo.mvp.presenter;


import com.cniao5.demo.mvp.model.User;
import com.cniao5.demo.mvp.view.MainView;

/**
 * 只为MainActivity提供业务逻辑
 * Created by xzhang
 */

public interface MainPresenter extends BasePresenter<MainView> {

    void login(User user);

}
