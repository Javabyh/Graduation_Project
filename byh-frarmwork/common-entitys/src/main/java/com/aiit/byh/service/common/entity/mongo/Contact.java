package com.aiit.byh.service.common.entity.mongo;

import java.io.Serializable;

/**
 * 手机通讯录信息
 *
 * @author dsqin
 * @datetime 2017/11/7
 */
public class Contact implements Serializable {

    private static final long serialVersionUID = 4422163623687980498L;

    /**
     * 用户编号
     */
    private String userid;

    /**
     * 手机号码
     */
    private String phoneno;

    public Contact() {

    }

    public Contact(String userID, String phoneNo) {
        this.userid = userID;
        this.phoneno = phoneNo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("userid='").append(userid).append('\'');
        sb.append(", phoneno='").append(phoneno).append('\'');
        return sb.toString();
    }
}