package com.cniao5.mvp.presenter;

import com.cniao5.mvp.model.GithubService;
import com.cniao5.mvp.model.Repo;
import com.cniao5.mvp.view.MainView;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Description:
 *
 * @author xzhang
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainBaseView ;
    private List<Repo> repoList ;
    private Subscription subscribe ;


    @Override
    public void attachView(MainView view) {
        this.mainBaseView = view ;
    }

    @Override
    public void detachView() {
        mainBaseView = null ;
        if(subscribe != null){
            subscribe.unsubscribe();
        }
    }

    @Override
    public void loadGitHubJava() {
        mainBaseView.showProgress();

        String url = "http://github.laowch.com/json/java_daily" ;
        subscribe = GithubService.Factory.create().javaRepositories(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        if (repoList != null) {
                            mainBaseView.showRecyclerView(repoList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainBaseView.showErrorMessage();
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        repoList = repos;
                    }
                });

    }
}
