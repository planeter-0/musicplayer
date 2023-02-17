package com.starnet.musicplayer.model.vo;

import lombok.Data;

/**
 * 用户值对象
 */
@Data
public class UserVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String icon;

}
