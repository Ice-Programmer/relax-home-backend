package com.xiang.home.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * User Login Request
 *
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2024/10/19 16:17
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * User Account
     */
    private String userAccount;

    /**
     * User Password
     */
    private String userPassword;
}