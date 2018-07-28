package com.video.repository.sysuser;

import com.video.entity.sysuser.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统管理员
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {

    /**
     * 根据用户名查询出用户
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 根据用户名密码查找用户
     * @param username
     * @param password
     * @return
     */
    SysUser findByUsernameAndPasswordAndDeleteFlagFalse(String username, String password);

    /**
     * 分页获取所有管理员
     * @param pageable
     * @return
     */
    Page<SysUser> findByDeleteFlagFalse(Pageable pageable);

    /**
     * 分页条件查询管理员
     * @param realname
     * @param pageable
     * @return
     */
    Page<SysUser> findByRealnameAndDeleteFlagFalse(String realname, Pageable pageable);

}
