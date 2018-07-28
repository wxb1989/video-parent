package com.video.web;

import com.video.entity.sysuser.SysUser;
import com.video.entity.sysuser.SysUserForm;
import com.video.service.sysuser.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoWebApplicationTests {



    SysUserService sysUserService;

    @Test
    public  void maina() {
        SysUserForm sysUser =new SysUserForm();
        sysUser.setUsername("admin");

        sysUserService.getSysUsers(sysUser);
    }
}
