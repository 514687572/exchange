package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.SmsCaptcha;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface SmsMapper {

    @Insert("insert into t_sms_captcha(mobile, code, type, lastTime)values(#{mobile},#{code},#{type},#{lastTime})")
    int addSmsCaptcha(@Param("mobile") String mobile, @Param("type") String type, @Param("code") String code, @Param("lastTime") Date lastTime);

    @Update("update t_sms_captcha set code=#{code}, lastTime=#{lastTime}, status=0 where mobile=#{mobile} and type=#{type}")
    int updateSmsCaptcha(@Param("mobile") String mobile, @Param("type") String type, @Param("code") String code, @Param("lastTime") Date lastTime);

    @Select("select * from t_sms_captcha where mobile=#{mobile} and type=#{type}")
    SmsCaptcha getSmsCaptchaByMobile(@Param("mobile") String mobile, @Param("type") String type);

    @Select("select * from t_sms_captcha where mobile=#{mobile} and type=#{type} and code=#{code}")
    SmsCaptcha getSmsCaptcha(@Param("mobile") String mobile, @Param("type") String type, @Param("code") String code);

    @Update("update t_sms_captcha set status=1 where mobile=#{mobile} and type=#{type}")
    int updateSmsCaptchaStatus(@Param("mobile") String mobile, @Param("type") String type);
}
