package com.savannah.controller;

import com.github.pagehelper.PageInfo;
import com.savannah.controller.vo.MyPage;
import com.savannah.controller.vo.UserVO;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.response.ReturnType;
import com.savannah.service.UserService;
import com.savannah.service.model.UserDTO;
import com.savannah.util.auth.Auth;
import com.savannah.util.auth.Group;
import com.savannah.util.validator.IsEmail;
import com.savannah.util.validator.ValidationResult;
import com.savannah.util.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author stalern
 * @date 2019/12/10~17:30
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class UserController {

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    private final UserService userService;
    private final HttpServletRequest httpServletRequest;
    private final ValidatorImpl validator;

    public UserController(UserService userService, HttpServletRequest httpServletRequest, ValidatorImpl validator) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
        this.validator = validator;
    }

    /**
     * 通过id得到用户信息
     * @param id 用户id
     * @return 用户信息-returnType
     * @throws ReturnException 用户不存在
     */
    @GetMapping("/getUser/{id}")
    public ReturnType getUser(@PathVariable("id")Integer id) throws ReturnException {
        UserDTO userDTO = userService.getUserById(id);
        UserVO userVO = convertFromDTO(userDTO);
        return ReturnType.create(userVO);
    }

    /**
     * 列出所有用户
     * @param myPage 页面
     * @return pageInfo格式的用户
     */
    @GetMapping("/listUser")
    @Auth(Group.ADMIN)
    public ReturnType listUser(MyPage myPage) {
        List<UserVO> userVoList = new ArrayList<>();
        userService.listUser(myPage).forEach(userDTO -> {
            try {
                userVoList.add(convertFromDTO(userDTO));
            } catch (ReturnException e) {
                e.printStackTrace();
            }
        });
        return ReturnType.create(new PageInfo<>(userVoList));
    }

    /**
     * 用户登陆接口
     * 参数格式为x-www-form-urlencoded
     * @param email 邮箱
     * @param pwd 密码
     * @return 登录成功，同时返回用户信息存储于前端(不包括密码)
     * @throws ReturnException 用户名不存在或密码错误
     */
    @PostMapping("/login")
    public ReturnType login(@IsEmail String email, String pwd) throws ReturnException, UnsupportedEncodingException, NoSuchAlgorithmException {

        // 入参校验
        if(StringUtils.isEmpty(email)|| StringUtils.isEmpty(email)){
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR);
        }

        // 用户登陆服务,用来校验用户登陆是否合法
        UserDTO userDTO = userService.validateLogin(email,pwd);
        //将登陆凭证加入到用户登陆成功的session内
        this.httpServletRequest.getSession().setAttribute(httpServletRequest.getHeader(Constant.X_REAL_IP), userDTO);

        return ReturnType.create(convertFromDTO(userDTO));
    }

    /**
     * 通过邮箱发送验证码
     * @param email 邮箱
     * @return ok
     */
    @GetMapping("/getOtp/{email}")
    public ReturnType getOtp(@IsEmail @PathVariable("email")String email) throws ReturnException {

        ValidationResult result =  validator.validate(email);
        if(result.isHasErrors()){
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        Random random = new Random();
        String optCode = String.valueOf(random.nextInt(999999) + 1000);
        // 使用redis将opt和email关联
        redisTemplate.opsForValue().set(email,optCode);
        // 发送验证码给邮箱
        System.out.println(email + " " + optCode);
        return ReturnType.create();
    }

    /**
     * 用户注册
     * @param userDTO 用户信息
     * @param optCode 验证码
     * @param plus 角色
     * @return 成功或失败
     * @throws ReturnException 返回异常
     */
    @PostMapping("/register/{otpCode}")
    public ReturnType register(@RequestBody UserDTO userDTO, @PathVariable("otpCode") String optCode, @RequestParam("plus")String plus) throws ReturnException, UnsupportedEncodingException, NoSuchAlgorithmException {

        String inSessionOtpCode = redisTemplate.opsForValue().get(userDTO.getEmail());
        if (! StringUtils.equals(optCode, inSessionOtpCode)){
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }
        if (StringUtils.equals(plus, Constant.ADMIN_ROLE)) {
            userDTO.setRole(String.valueOf(Group.ADMIN));
        } else if (StringUtils.equals(plus, Constant.SELLER_ROLE)) {
            userDTO.setRole(String.valueOf(Group.SELLER));
        } else {
            userDTO.setRole(String.valueOf(Group.BUYER));
        }
        userService.register(userDTO);
        return ReturnType.create();
    }

    /**
     * 用户退出
     * @return 公共则返回ok
     */
    @GetMapping("/logout")
    public ReturnType logout() {
        this.httpServletRequest.getSession().setAttribute(httpServletRequest.getHeader(Constant.X_REAL_IP), null);
        return ReturnType.create();
    }

    private UserVO convertFromDTO(UserDTO userDTO) throws ReturnException {
        if (userDTO == null) {
            throw new ReturnException(EmReturnError.USER_NOT_EXIST);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDTO, userVO);
        return userVO;
    }
}
