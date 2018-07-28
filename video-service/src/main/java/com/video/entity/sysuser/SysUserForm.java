package com.video.entity.sysuser;


import com.video.core.base.BaseForm;

public class SysUserForm extends BaseForm {

	private static final long serialVersionUID = -8869164628622251162L;
	private int id;
	/** 用户名 **/
	private String username;
	/** 密码 **/
	private String password;
	/** 真实姓名 **/
	private String realname;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
