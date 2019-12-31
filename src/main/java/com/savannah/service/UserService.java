package com.savannah.service;

import com.savannah.controller.vo.MyPage;
import com.savannah.error.ReturnException;
import com.savannah.service.model.UserDTO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author stalern
 * @date 2019/12/09~21:36
 */
public interface UserService {

    /**
     * 求出所有用户
     * @param myPages 传递第几页和数量
     * @return 所有用户信息
     */
    List<UserDTO> listUser(MyPage myPages);
    /**
     * 通过id返回用户
     * @param id 主键
     * @return UserDTO
     */
    UserDTO getUserById(Integer id);

    /**
     * 用户注册
     * @param userDTO 用户信息
     * @throws ReturnException NPE参数异常
     */
    void register(UserDTO userDTO) throws ReturnException, UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 核实登录
     * @param email 邮箱
     * @param pwd 未加密密码
     * @return UserDTO
     * @throws ReturnException 账号或密码错误
     */
    UserDTO validateLogin(String email, String pwd) throws ReturnException, UnsupportedEncodingException, NoSuchAlgorithmException;
}
