package com.heshun.firstkotlin.mvp.hongyang.biz;

public interface IUserBiz
{
	public void login(String username, String password, OnLoginListener loginListener);
}