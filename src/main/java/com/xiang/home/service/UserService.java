package com.xiang.home.service;

import com.xiang.home.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiang.home.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenjiahan
 * @description Database Operation Service for the "user" Table
 * @createDate 2024-10-09 08:27:52
 */
public interface UserService extends IService<User> {

    /**
     * User Register
     *
     * @param userAccount   user account
     * @param userPassword  user password
     * @param checkPassword check password
     * @param userRole      user role
     * @return new User id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String userRole);


    /**
     * user login
     *
     * @param userAccount  user account
     * @param userPassword user password
     * @param request      cookie
     * @return Redacted User Information
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * Retrieve sanitized information of the authenticated user.
     *
     * @return sanitized information of the authenticated user
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * Acquiring Current Login User
     *
     * @param request cookie
     * @return Current Login User
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * User Logout
     *
     * @param request cookie
     * @return User Logout Successfully
     */
    boolean userLogout(HttpServletRequest request);

}
