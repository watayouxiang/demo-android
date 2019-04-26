package com.cniao5.mvp.presenter;

import com.cniao5.mvp.model.GithubService;
import com.cniao5.mvp.model.Repository;
import com.cniao5.mvp.view.UserRepoView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Description:
 *
 * @author xzhang
 */

public class UserRepoPresenterImpl implements UserRepoPresenter {

    private UserRepoView userRepoBaseView ;
    private List<Repository> repositoryList ;

    @Override
    public void attachView(UserRepoView view) {
        this.userRepoBaseView = view ;
    }

    @Override
    public void detachView() {
        this.userRepoBaseView = null ;
    }

    @Override
    public void loadGitHubUserRepo(String userName) {

        userRepoBaseView.showProgress();
        GithubService.Factory.create().publicRepositories(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repository>>() {

                    @Override
                    public void onCompleted() {
                        if(repositoryList != null){
                            userRepoBaseView.showRecyclerView(repositoryList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        userRepoBaseView.showErrorMessage();
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        repositoryList = repositories ;
                    }
                });
    }
}
