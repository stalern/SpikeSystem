package com.savannah.controller;

import com.savannah.controller.vo.UserVO;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.response.ReturnType;
import com.savannah.service.UserService;
import com.savannah.service.model.UserDTO;
import com.savannah.util.validator.ValidationResult;
import com.savannah.util.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author stalern
 * @date 2019/12/10~17:30
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class UserController extends BaseController {

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
     * 用户登陆接口
     * 参数格式为x-www-form-urlencoded
     * @param email 邮箱
     * @param pwd 密码
     * @return 登录成功
     * @throws ReturnException 用户名不存在或密码错误
     */
    @PostMapping("/login")
    public ReturnType login(String email, String pwd) throws ReturnException {

        // 入参校验
        if(StringUtils.isEmpty(email)|| StringUtils.isEmpty(email)){
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR);
        }

        // 用户登陆服务,用来校验用户登陆是否合法
        UserDTO userDTO = userService.validateLogin(email,pwd);
        //将登陆凭证加入到用户登陆成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userDTO);

        return ReturnType.create();
    }

    /**
     * 通过邮箱发送验证码
     * @param email 邮箱
     * @return ok
     */
    @GetMapping("/getOtp/{email}")
    public ReturnType getOtp(@PathVariable("email")String email) {
        Random random = new Random();
        String optCode = String.valueOf(random.nextInt(999999) + 1000);
        // 使用HttpSession将opt和email关联
        httpServletRequest.getSession().setAttribute(email, optCode);
        // 发送验证码给邮箱
        System.out.println(email + " " + optCode);
        return ReturnType.create();
    }

    @PostMapping(value = "/register/{otpCode}")
    public ReturnType register(@RequestBody UserDTO userDTO, @PathVariable("otpCode") String optCode) throws ReturnException {

        ValidationResult result =  validator.validate(userDTO);
        if(result.isHasErrors()){
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(userDTO.getEmail());
        if (! StringUtils.equals(optCode, inSessionOtpCode)){
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }
        userService.register(userDTO);
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
