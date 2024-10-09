package com.xiang.home.controller;

import com.xiang.home.common.BaseResponse;
import com.xiang.home.common.ErrorCode;
import com.xiang.home.common.ResultUtils;
import com.xiang.home.exception.BusinessException;
import com.xiang.home.model.dto.user.UserLoginRequest;
import com.xiang.home.model.dto.user.UserRegisterRequest;
import com.xiang.home.model.entity.User;
import com.xiang.home.model.vo.LoginUserVO;
import com.xiang.home.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User API
 *
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2024/10/9 08:29
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user", tags = "User Controller")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * User Register
     *
     * @param userRegisterRequest User Register Request
     * @return new User id
     */
    @PostMapping("/register")
    @ApiOperation(value = "User Register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userRole = userRegisterRequest.getUserRole();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, userRole)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, userRole);
        return ResultUtils.success(result);
    }


    /**
     * User Login
     *
     * @param userLoginRequest User Login Request
     * @param request          Cookie
     * @return Redacted User Information
     */
    @PostMapping("/login")
    @ApiOperation(value = "User Login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * Acquiring Current Login User
     *
     * @param request cookie
     * @return Current Login User
     */
    @GetMapping("/get/login")
    @ApiOperation(value = "Get Current Login User")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    /**
     * User Logout
     *
     * @param request cookie
     * @return User logout successfully
     */
    @PostMapping("/logout")
    @ApiOperation(value = "Current Logged-in User Logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }
}
