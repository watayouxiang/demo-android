package com.cniao5.mvp.presenter;

import com.cniao5.mvp.view.MainView;

/**
 * <p>Description:
 * 处理MainActivity当中的业务逻辑,只为MainActivity服务
 *
 * @author xzhang
 */

public interface MainPresenter extends BasePresenter<MainView> {
    //在这里添加MainPresenter特有的方法
    void loadGitHubJava() ;
}
