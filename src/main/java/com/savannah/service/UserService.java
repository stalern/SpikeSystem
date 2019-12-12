package com.savannah.service;

import com.savannah.error.ReturnException;
import com.savannah.service.model.UserDTO;

/**
 * @author stalern
 * @date 2019/12/09~21:36
 */
public interface UserService {
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
    void register(UserDTO userDTO) throws ReturnException;

    /**
     * 核实登录
     * @param email 邮箱
     * @param pwd 未加密密码
     * @return UserDTO
     * @throws ReturnException 账号或密码错误
     */
    UserDTO validateLogin(String email, String pwd) throws ReturnException;
}
