package com.xiang.home.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * View for Logged-In Users (redacted)
 *
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2024/10/9 08:33
 */
@Data
public class LoginUserVO implements Serializable {

    /**
     * user id
     */
    private Long id;

    /**
     * user nickname
     */
    private String userName;

    /**
     * user avatar
     */
    private String userAvatar;

    /**
     * user profile
     */
    private String userProfile;

    /**
     * user role：tenant/landlord/admin/ban
     */
    private String userRole;

    /**
     * create time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}
