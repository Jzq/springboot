package com.jzq.modules.sys.service.impl;

import com.jzq.modules.sys.dao.UserInfoMapper;
import com.jzq.modules.sys.entity.UserInfo;
import com.jzq.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public List<UserInfo> getUserList(UserInfo userInfo) {
        return this.userInfoMapper.getAllList();
    }
}
