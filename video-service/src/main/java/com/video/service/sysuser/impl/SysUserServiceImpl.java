package com.video.service.sysuser.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.video.core.utils.MD5Utils;
import com.video.entity.sysuser.SysUser;
import com.video.entity.sysuser.SysUserForm;
import com.video.repository.sysuser.SysUserRepository;
import com.video.service.sysuser.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser checkSysUserLogin(String username, String password) {
        return sysUserRepository.findByUsernameAndPasswordAndDeleteFlagFalse(username, password);
    }

    @Override
    public Page<SysUser> getSysUsers(SysUserForm form) {

        Page<SysUser> sysUserPage ;

        String realname = form.getRealname();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(form.getPage(), form.getCount(), sort);

        if (StringUtils.isBlank(realname)) {
            sysUserPage = sysUserRepository.findByDeleteFlagFalse(pageable);
        }
        else {
            sysUserPage = sysUserRepository.findByRealnameAndDeleteFlagFalse(realname, pageable);
        }

        return sysUserPage;

    }
    @Transactional
    @Override
    public void deleteSysUsers(String jsonData) {

        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject job = jsonArray.getJSONObject(i);
            int id = job.getInteger("id");
            SysUser sysUser = sysUserRepository.getOne(id);
            sysUser.setDeleteFlag(true);
            sysUserRepository.save(sysUser);
        }

    }

    @Override
    public SysUser addSysUser(SysUserForm form) {
        SysUser sysUser = new SysUser();
        sysUser.setCreatedDate(new Date());
        sysUser.setUsername(form.getUsername());
        sysUser.setPassword(MD5Utils.generateMD5("123456"));
        sysUser.setRealname(form.getRealname());
        sysUserRepository.save(sysUser);

        return sysUser;

    }

    @Override
    public SysUser updateSysUser(SysUserForm form) {
        SysUser sysUser = sysUserRepository.getOne(form.getId());
        sysUser.setUpdatedDate(new Date());
        sysUser.setRealname(form.getRealname());
        sysUserRepository.save(sysUser);
        return sysUser;

    }

}
