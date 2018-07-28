package com.video.entity.sysuser;


import com.video.core.base.BaseModel;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

/**
 * 系统用户信息
 */
@Data
@Entity
@Table(name = "t_sysuser")
public class SysUser extends BaseModel {

	/** 用户名 **/
	@Column(length=20,nullable=false)
	private String username;

	/** 密码 **/
	@Column(length=100,nullable=false)
	private String password;

	/** 真实姓名 **/
	@Column(length=20,nullable=false)
	private String realname;

	/**
	 * 关联权限
	 */
	@ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
	private List<SysRole> roles;


}
