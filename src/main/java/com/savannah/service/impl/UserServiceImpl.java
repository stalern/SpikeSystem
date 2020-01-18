package com.savannah.service.impl;

import com.github.pagehelper.PageHelper;
import com.savannah.controller.vo.MyPage;
import com.savannah.dao.UserInfoMapper;
import com.savannah.dao.UserPwdMapper;
import com.savannah.dao.UserRoleMapper;
import com.savannah.entity.UserInfoDO;
import com.savannah.entity.UserPwdDO;
import com.savannah.entity.UserRoleDO;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.service.UserService;
import com.savannah.service.model.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

/**
 * @author stalern
 * @date 2019/12/10~17:31
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userInfoMapper;
    private final UserPwdMapper userPwdMapper;
    private final UserRoleMapper userRoleMapper;

    public UserServiceImpl(UserInfoMapper userInfoMapper, UserPwdMapper userPwdMapper, UserRoleMapper userRoleMapper) {
        this.userInfoMapper = userInfoMapper;
        this.userPwdMapper = userPwdMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<UserDTO> listUser(MyPage myPages) {
        PageHelper.startPage(myPages.getPage(),myPages.getSize());
        return userInfoMapper.listUserInfo();
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
    public void register(UserDTO userDTO) throws ReturnException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (userDTO == null) {
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR);
        }
        userDTO.setPwd(encodeByMd5(userDTO.getPwd()));
        UserInfoDO userInfoDO = convertInfoFromDTO(userDTO);
        try {
            userInfoMapper.insertSelective(userInfoDO);
        } catch (DuplicateKeyException ex) {
            throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR,"邮箱已经被注册");
        }
        userDTO.setId(userInfoDO.getId());
        UserPwdDO userPwdDO = convertPwdFromDTO(userDTO);
        userPwdMapper.insertSelective(userPwdDO);
        UserRoleDO userRoleDO = convertRoleFromDTO(userDTO);
        userRoleMapper.insertSelective(userRoleDO);
    }

    @Override
    public UserDTO validateLogin(String email, String pwd) throws ReturnException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserInfoDO userInfoDO = userInfoMapper.selectByEmail(email);
        if (userInfoDO == null) {
            throw new ReturnException(EmReturnError.USER_LOGIN_FAIL);
        }
        UserPwdDO userPwdDO = userPwdMapper.selectByUserId(userInfoDO.getId());
        UserRoleDO userRoleDO = userRoleMapper.selectByUserId(userInfoDO.getId());
        UserDTO userDTO = convertFromDO(userInfoDO,userPwdDO,userRoleDO);
        if (!StringUtils.equals(encodeByMd5(pwd), userDTO.getPwd())) {
            throw new ReturnException(EmReturnError.USER_LOGIN_FAIL);
        }
        return userDTO;
    }

    private UserRoleDO convertRoleFromDTO(UserDTO userDTO) {
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setRole(userDTO.getRole());
        userRoleDO.setUserId(userDTO.getId());
        return userRoleDO;
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

    public String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(str.getBytes());
    }
}
