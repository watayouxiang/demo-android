package com.cniao5.demo.mvp.presenter;

import android.text.TextUtils;

import com.cniao5.demo.mvp.model.User;
import com.cniao5.demo.mvp.view.MainView;


/**
 * MainActivity业务逻辑的具体实现
 * Created by xzhang
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mainBaseView;

    @Override
    public void attachView(MainView v) {
        this.mainBaseView = v;
    }

    @Override
    public void detachView() {
        mainBaseView = null;
    }

    @Override
    public void login(User user) {
        //模拟网络登录
        if (!TextUtils.isEmpty(user.getName()) && !TextUtils.isEmpty(user.getPwd())) {
            if (user.getName().equals("zhangsan") && user.getPwd().equals("123456")) {
                mainBaseView.loginSuccess("登录成功");
            } else {
                mainBaseView.loginFailed("登录失败");
            }
        } else {
            mainBaseView.showToast("用户名或密码不能为空");
        }
    }
}
