package com.kang.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.usercenter.common.ErrorCode;
import com.kang.usercenter.exception.BusinessException;
import com.kang.usercenter.model.domain.User;
import com.kang.usercenter.service.UserService;
import com.kang.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kang.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 26444
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-10-11 21:02:29
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;

    final String SALT = "yupi";

    /**
     * 注册用户
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 二次确认密码
     * @param planetCode 用户编号
     * @return 用户id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if(userPassword.length()<8 ||userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(planetCode.length()>5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户编号过长");
        }
        //校验特殊字符
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher m = Pattern.compile(regEx).matcher(userAccount);
        if(m.find()){
            return -1;
        }
        //校验用户名不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count>0){
            return -1;
        }

        QueryWrapper<User> queryWrappers = new QueryWrapper<>();
        queryWrappers.eq("planetCode",planetCode);
        long counts = userMapper.selectCount(queryWrapper);
        if(counts>0){
            return -1;
        }

        //对密码进行MD5加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean result = this.save(user);
        if(!result){
            return -1;
        }
        return user.getId();
    }

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @return 用户脱敏信息
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }

        if(userPassword.length()<8 ||userAccount.length()<4){
            return null;
        }
        //校验特殊字符
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher m = Pattern.compile(regEx).matcher(userAccount);
        if(m.find()){
            return null;
        }
        //查看用户是否存在
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        if(user == null){
            log.info("user login failed");
            return null;
        }
        //对用户信息进行脱敏
        User safetyUser = getSafetyUser(user);

        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);
        return safetyUser;
    }
    @Override
    public User getSafetyUser(User user){
        if(user == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setGender(user.getGender());
        safetyUser.setPlanetCode(user.getPlanetCode());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("USER_LOGIN_STATE");
        return 1;
    }
}




