package com.xiang.home.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.home.common.ErrorCode;
import com.xiang.home.constant.UserConstant;
import com.xiang.home.exception.BusinessException;
import com.xiang.home.model.entity.User;
import com.xiang.home.model.enums.UserRoleEnum;
import com.xiang.home.model.vo.LoginUserVO;
import com.xiang.home.service.UserService;
import com.xiang.home.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.xiang.home.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author chenjiahan
 * @description Implementation of the Database Operation Service for the "user" Table
 * @createDate 2024-10-09 08:27:52
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * Salt Value, Password Obfuscation
     */
    public static final String SALT = "xiang";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String userRole) {
        // 1. Verification
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "The parameters are empty!");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "The user account is too short in length!");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "The user password is excessively brief");
        }
        if (!UserRoleEnum.getValues().contains(userRole)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "The user role is not valid!");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "The entered passwords do not match each other");
        }
        synchronized (userAccount.intern()) {
            // An account cannot be duplicated
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "Account cannot be duplicated!");
            }
            // 2. Encryption
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. Insert Data
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            String userName = UserConstant.USER_NAME_PREFIX + RandomUtil.randomString(6);
            user.setUserName(userName);
            user.setUserRole(userRole);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "The registration has failed due to a database error！");
            }
            return user.getId();
        }
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. Verification
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "The parameters are empty!");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid account credentials!");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Incorrect password provided!");
        }
        // 2. Encryption
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // Verify the existence of the user
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptPassword));
        // The user does not exist in the system
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "The user does not exist or the password is incorrect.");
        }
        // 3. Preserve the login state of the user
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        // Prior to proceeding, it is essential to ascertain if a user is currently logged in
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // Retrieve data from the database
        long userId = currentUser.getId();
        currentUser = this.baseMapper.selectById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "Not Login yet!");
        }
        // remove user login state
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }
}




