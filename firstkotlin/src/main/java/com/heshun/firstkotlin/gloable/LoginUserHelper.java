package com.heshun.firstkotlin.gloable;

/**
 * 单例的用户缓存，存储当前登陆用户的基本信息
 * 
 * @author huangxz
 */
public class LoginUserHelper {
	private static LoginUserHelper instance;

	private String currOrderNumber = "";

	private CommonUser loginUser;

	private String currGid;

	private LoginUserHelper() {

	}

	public static LoginUserHelper getHelper() {
		synchronized (LoginUserHelper.class) {
			if (null == instance) {
				instance = new LoginUserHelper();
			}
			return instance;
		}
	}

	public CommonUser getUserInfo() {
		return loginUser;
	}

	public boolean isLogined() {
		return loginUser != null;
	}


	public void setLoginUser(CommonUser user) {
		loginUser = user;
	}

	public void logOut() {
		loginUser = null;
	}

	public String getOrderNum() {
		return currOrderNumber;
	}

	public void setOrderNum(String orderNum) {
		this.currOrderNumber = orderNum;
	}

	public String getCurrGid() {
		return currGid;
	}

	public LoginUserHelper setCurrGid(String currGid) {
		this.currGid = currGid;
		return this;
	}

}
