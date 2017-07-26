package com.heshun.firstkotlin.mvp.hongyang.view;


import com.heshun.firstkotlin.mvp.hongyang.bean.User;

public interface IUserLoginView
{
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();

}