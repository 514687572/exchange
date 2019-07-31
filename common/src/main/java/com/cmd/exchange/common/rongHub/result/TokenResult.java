package com.cmd.exchange.common.rongHub.result;

import com.cmd.exchange.common.rongHub.util.GsonUtil;
import io.swagger.annotations.ApiModelProperty;

/**
 * getToken 返回结果
 */
public class TokenResult extends Result {
    @ApiModelProperty("用户Token，可以保存应用内，长度在 256 字节以内.融云sdk专用")
    String token;
    @ApiModelProperty("用户 Id，与输入的用户 Id 相同.")
    String userId;

    public TokenResult(Integer code, String token, String userId, String errorMessage) {
        this.code = code;
        this.token = token;
        this.userId = userId;
        this.msg = errorMessage;
    }

    /**
     * 设置token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取token
     *
     * @return String
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取userId
     *
     * @return String
     */
    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this, TokenResult.class);
    }
}
