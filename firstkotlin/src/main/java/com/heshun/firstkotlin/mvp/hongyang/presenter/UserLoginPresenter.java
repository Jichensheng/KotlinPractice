package com.heshun.firstkotlin.mvp.hongyang.presenter;

import android.os.Handler;

import com.heshun.firstkotlin.mvp.hongyang.bean.User;
import com.heshun.firstkotlin.mvp.hongyang.biz.IUserBiz;
import com.heshun.firstkotlin.mvp.hongyang.biz.OnLoginListener;
import com.heshun.firstkotlin.mvp.hongyang.biz.UserBiz;
import com.heshun.firstkotlin.mvp.hongyang.view.IUserLoginView;


public class UserLoginPresenter
{
    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(IUserLoginView userLoginView)
    {
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
    }

    public void login()
    {
        userLoginView.showLoading();
        userBiz.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener()
        {
            @Override
            public void loginSuccess(final User user)
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });

            }

            @Override
            public void loginFailed()
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });

            }
        });
    }

    public void clear()
    {
        userLoginView.clearUserName();
        userLoginView.clearPassword();
    }



}