package com.starnet.musicplayer.service;


import com.starnet.musicplayer.model.param.RegisterParam;
import com.starnet.musicplayer.model.entity.UserEntity;
import com.starnet.musicplayer.model.vo.UserVO;

public interface UserService {
    /**
     * 注册邮箱验证
     * @param email
     */
    void verifyEmail(String email);

    /**
     * 注册
     * @param param
     * @return 用户id
     */
    Long register(RegisterParam param);

    /**
     * 更新用户
     * @param user
     * @return
     */
    UserEntity update(UserVO user);

    /**
     * 根据用户名获取用户实体
     * @param username
     * @return
     */
    UserEntity getByUsername(String username);
}
