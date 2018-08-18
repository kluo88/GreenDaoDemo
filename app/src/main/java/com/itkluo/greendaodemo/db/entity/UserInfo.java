package com.itkluo.greendaodemo.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * 用户个人资料
 * 不存db的字段要加  @Transient
 * <p>
 * Created by luobingyong on 2018/8/17.
 */
@Entity
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 6299300192544739072L;
    //    @Id(autoincrement = true)
//    public Long id;
    @Id
    public Long user_id;
    public String username;
    public String mobile;

    @Transient
    public int loginCount;

    @Generated(hash = 560249574)
    public UserInfo(Long user_id, String username, String mobile) {
        this.user_id = user_id;
        this.username = username;
        this.mobile = mobile;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", loginCount=" + loginCount +
                '}' + "\n";
    }
}
