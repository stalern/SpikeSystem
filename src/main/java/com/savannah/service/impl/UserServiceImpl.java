package com.savannah.service.impl;

import com.savannah.dao.UserInfoMapper;
import com.savannah.dao.UserPwdMapper;
import com.savannah.dao.UserRoleMapper;
import com.savannah.dataobject.UserInfoDO;
import com.savannah.dataobject.UserPwdDO;
import com.savannah.dataobject.UserRoleDO;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.service.UserService;
import com.savannah.service.model.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author stalern
 * @date 2019/12/10~17:31
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userInfoMapper;

    @Autowired
    private UserPwdMapper userPwdMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;
    public UserServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public UserDTO getUserById(Integer id) {
        UserInfoDO userInfoDO = userInfoMapper.selectByPrimaryKey(id);
        if (userInfoDO == null) {
            return null;
        }
        UserPwdDO userPwdDO = userPwdMapper.selectByUserId(userInfoDO.getId());
        UserRoleDO userRoleDO = userRoleMapper.selectByUserId(userInfoDO.getId());
        return convertFromDO(userInfoDO,userPwdDO,userRoleDO);
    }

    @Override
    @Transactional(rollbackFor = ReturnException.class)
    public void register(UserDTO userDTO) throws ReturnException {
        if (userDTO == null) {
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR);
        }

        UserInfoDO userInfoDO = convertInfoFromDTO(userDTO);
        try {
            userInfoMapper.insertSelective(userInfoDO);
        } catch (DuplicateKeyException ex) {
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR,"邮箱已经被注册");
        }
        userDTO.setId(userInfoDO.getId());
        UserPwdDO userPwdDO = convertPwdFromDTO(userDTO);
        userPwdMapper.insertSelective(userPwdDO);
    }

    @Override
    public UserDTO validateLogin(String email, String pwd) throws ReturnException {
        UserInfoDO userInfoDO = userInfoMapper.selectByEmail(email);
        if (userInfoDO == null) {
            throw new ReturnException(EmReturnError.USER_LOGIN_FAIL);
        }
        UserPwdDO userPwdDO = userPwdMapper.selectByUserId(userInfoDO.getId());
        UserRoleDO userRoleDO = userRoleMapper.selectByUserId(userInfoDO.getId());
        UserDTO userDTO = convertFromDO(userInfoDO,userPwdDO,userRoleDO);
        if (!StringUtils.equals(pwd, userDTO.getPwd())) {
            throw new ReturnException(EmReturnError.USER_LOGIN_FAIL);
        }
        return userDTO;
    }

    private UserPwdDO convertPwdFromDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        UserPwdDO userPwdDO = new UserPwdDO();
        userPwdDO.setUserId(userDTO.getId());
        userPwdDO.setEncryptedPwd(userDTO.getPwd());
        return userPwdDO;
    }

    private UserInfoDO convertInfoFromDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        UserInfoDO userInfoDO = new UserInfoDO();
        BeanUtils.copyProperties(userDTO, userInfoDO);
        return userInfoDO;
    }

    private UserDTO convertFromDO(UserInfoDO userInfoDO, UserPwdDO userPwdDO, UserRoleDO userRoleDO) {
        if (userInfoDO == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfoDO, userDTO);
        if (userPwdDO != null) {
            userDTO.setPwd(userPwdDO.getEncryptedPwd());
        }
        if (userRoleDO != null) {
            userDTO.setRole(userRoleDO.getRole());
        }
        return userDTO;
    }
}
