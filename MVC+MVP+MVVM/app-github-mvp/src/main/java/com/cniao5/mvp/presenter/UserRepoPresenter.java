package com.cniao5.mvp.presenter;

import com.cniao5.mvp.view.UserRepoView;

/**
 * <p>Description:
 * 只为UserRepoActivity提供业务逻辑
 *
 * @author xzhang
 */

public interface UserRepoPresenter  extends BasePresenter<UserRepoView>{
    //添加UserRepoPresenter中特有的业务逻辑

    void loadGitHubUserRepo(String userName) ;
}
