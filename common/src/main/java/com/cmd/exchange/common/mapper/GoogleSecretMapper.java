package com.cmd.exchange.common.mapper;


import com.cmd.exchange.common.model.GoogleAuth;
import com.cmd.exchange.common.model.GoogleSecret;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Mapper
public interface GoogleSecretMapper {

    int add(GoogleSecret googleSecret);

    int updateGoogleSecret(GoogleSecret googleSecret);

    GoogleSecret getGoogleSecret(@Param("userId") Integer userId);


    int addGoogleAuth(GoogleAuth googleAuth);

    int updateGoogleAuth(GoogleAuth googleAuth);

    GoogleAuth getGoogleAuthByMobileAndType(@Param("mobile") String mobile, @Param("type") String type);
}
