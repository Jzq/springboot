package com.jzq.modules.sys.service;


import com.jzq.modules.sys.entity.UserInfo;

import java.util.List;

public interface UserService {

    public List<UserInfo> getUserList(UserInfo userInfo);
}
