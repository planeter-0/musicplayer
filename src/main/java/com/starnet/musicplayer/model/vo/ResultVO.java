package com.starnet.musicplayer.model.vo;

import com.starnet.musicplayer.common.enums.ResultCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * 自定义统一响应体
 */
@Getter
public class ResultVO<T> implements Serializable {
    /**
     * 状态码, 默认1000是成功
     */
    private final int code;
    /**
     * 响应信息, 来说明响应情况
     */
    private final String msg;
    /**
     * 响应的具体数据
     */
    private T data;

    public ResultVO() {
        this(ResultCode.SUCCESS);
    }

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }
    public ResultVO(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }
    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":%d,\"msg\":\"%s\",\"data\":\"%s\"}", code, msg, data.toString());
    }
}
