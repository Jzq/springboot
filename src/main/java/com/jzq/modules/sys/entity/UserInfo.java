/*
*
* UserInfo.java
* Copyright(C) PCITC
* @date 2018-08-17
*/
package com.jzq.modules.sys.entity;


import java.io.Serializable;

public class UserInfo implements Serializable{

    private static final long serialVersionUID = -812027227723964094L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 
     */
    private String loginName;

    private String password;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户id
     * @return uid 用户id
     */
    public String getUid() {
        return uid;
    }

    /**
     * 用户id
     * @param uid 用户id
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 用户姓名
     * @return name 用户姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 用户姓名
     * @param name 用户姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 年龄
     * @return age 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 年龄
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 
     * @return loginName 
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 
     * @param loginName 
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }
}