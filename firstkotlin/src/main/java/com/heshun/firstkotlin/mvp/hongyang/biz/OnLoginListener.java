package com.heshun.firstkotlin.mvp.hongyang.biz;


import com.heshun.firstkotlin.mvp.hongyang.bean.User;

public interface OnLoginListener
{
	void loginSuccess(User user);

    void loginFailed();
}