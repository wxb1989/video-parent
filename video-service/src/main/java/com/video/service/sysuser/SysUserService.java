package com.video.service.sysuser;

import com.video.entity.sysuser.SysUser;
import com.video.entity.sysuser.SysUserForm;
import org.springframework.data.domain.Page;

public interface SysUserService {

    /**
     * 验证用户登录
     * @param username
     * @param password
     * @return
     */
    public SysUser checkSysUserLogin(String username, String password);

    /**
     * 分页获取管理员
     * @param form
     * @return
     */
    public Page<SysUser> getSysUsers(SysUserForm form);

    /**
     * 删除
     * @param jsonData 需要删除的json数据
     */
    public void deleteSysUsers(String jsonData);

    /**
     * 新增
     * @param form
     * @return
     */
    public SysUser addSysUser(SysUserForm form);

    /**
     * 修改
     * @param form
     * @return
     */
    public SysUser updateSysUser(SysUserForm form);

}
