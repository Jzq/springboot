/*
*
* UserInfoMapper.java
* Copyright(C) PCITC
* @date 2018-08-17
*/
package com.jzq.modules.sys.dao;

import com.jzq.modules.sys.entity.UserInfo;
import org.apache.catalina.User;

import java.util.List;

public interface UserInfoMapper {
    /**
     *
     * @mbggenerated 2018-08-17
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-17
     */
    int insert(UserInfo record);

    /**
     *
     * @mbggenerated 2018-08-17
     */
    int insertSelective(UserInfo record);

    /**
     *
     * @mbggenerated 2018-08-17
     */
    UserInfo selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-08-17
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     *
     * @mbggenerated 2018-08-17
     */
    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> getAllList();
}