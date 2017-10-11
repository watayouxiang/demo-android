package com.cniao5.demo.mvp.presenter;


import com.cniao5.demo.mvp.view.ViewBaseOther;

/**
 *
 * 只为OtherActivity提供业务逻辑
 * Created by xzhang
 */

public interface PresenterOther extends PresenterBase<ViewBaseOther> {



    void uploadImage(String path) ;
}
