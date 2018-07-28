package com.video.service;

import com.video.entity.sysuser.SysUserForm;
import com.video.service.sysuser.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoServiceApplicationTests {

    Logger logger = LoggerFactory.getLogger(VideoServiceApplicationTests.class);


    SysUserService sysUserService;

    @Test
    public  void maina() {
        sysUserService.getSysUsers(new SysUserForm());
    }

}
