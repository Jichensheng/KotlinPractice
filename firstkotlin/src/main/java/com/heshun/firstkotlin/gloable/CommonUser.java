package com.heshun.firstkotlin.gloable;

import java.util.Map;

public class CommonUser {
	public String address = "";
	public int area;
	public int city;
	public String decInfo;
	public String headImage="";
	public String mobile = "";
	public int money;
	public String nickName = "";
	public Map<?, ?> org;
	public int powerPackage;
	public int province;
	public String pwd;
	public String regIp;
	public String sex = "男";
	public String token;
	public String status;

	@Override
	public boolean equals(Object o) {

		CommonUser u = (CommonUser) o;
		return this.address.equals(u.address) && this.sex.equals(u.sex) && this.nickName.equals(u.nickName);
	}

}
