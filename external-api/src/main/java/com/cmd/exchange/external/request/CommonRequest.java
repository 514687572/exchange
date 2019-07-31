package com.cmd.exchange.external.request;

import com.cmd.exchange.external.utils.SignUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class CommonRequest {
    @NotBlank
    @ApiModelProperty(value = "用户的api key", required = true)
    protected String apiKey;
    @NotNull
    @ApiModelProperty(value = "unix时间戳，单位秒， UTC", required = true)
    protected Integer timestamp;
    @NotBlank
    @ApiModelProperty(value = "签名值, 把所有参数（sign除外）按字母升序排序, 在最后加上&secret=用户的API secret，然后取MD5的值", required = true)
    @JsonProperty("sign")
    protected String sign;

    @ApiModelProperty(value = "签名类型, HA256或者MD5， 默认MD5", hidden = true)
    @JsonIgnore
    protected String signType;

    public boolean isValidSign(String apiSecret) {
        String expectedSign = getSign(apiSecret).toLowerCase();
        String actualSign = sign.toLowerCase();

        return actualSign.equals(expectedSign);
    }

    //请求的时间戳和服务器的时间戳必须在30秒以内
    @JsonIgnore
    @ApiIgnore
    @ApiModelProperty(value = "签名类型, HA256或者MD5， 默认MD5", hidden = true)
    public boolean isValidTimestamp() {
        return timestamp < System.currentTimeMillis() / 1000l + 30
                && timestamp > System.currentTimeMillis() / 1000l - 30;
    }

    private String getSign(String hashKey) {
        Map<String, String> map = new HashMap<>();

        for (Field f : this.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (!f.getName().equals("sign") && !f.getName().equals("DEFAULT_SIGN_TYPE")) {
                try {
                    if (f.get(this) != null) {
                        map.put(f.getName(), f.get(this).toString());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Class parentCls = this.getClass().getSuperclass();
        while (!parentCls.getSimpleName().equals("Object")) {
            for (Field f : parentCls.getDeclaredFields()) {
                f.setAccessible(true);
                if (!f.getName().equals("sign") && !f.getName().equals("DEFAULT_SIGN_TYPE")) {
                    try {
                        map.put(f.getName(), f.get(this) == null ? null : f.get(this).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            parentCls = parentCls.getSuperclass();
        }

        if (getSignType() == null || getSignType().toLowerCase().equals("md5")) {
            return SignUtil.getSignString("md5", hashKey, map);
        }

        return SignUtil.getSignString("sha-256", hashKey, map);
    }
}
