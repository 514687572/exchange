package com.cmd.exchange.admin.vo;

import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.vo.UserInfoVO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

@Data
public class UserExportVO extends User {
    private String idCardUploadString;
    private String authStatusString;
    private String statusString;

    public static UserExportVO fromModel(UserInfoVO user) {
        UserExportVO vo = new UserExportVO();
        BeanUtils.copyProperties(user, vo);

        if (user.getStatus() == 0) {
            vo.setStatusString("正常");
        } else if (user.getStatus() == 1) {
            vo.setStatusString("禁用");
        }

        if (StringUtils.isBlank(user.getIdCardImg1())) {
            vo.setIdCardUploadString("未上传");
        } else {
            vo.setIdCardUploadString("已上传");
        }

        if (user.getIdCardStatus() == 0) {
            vo.setAuthStatusString("未实名认证");
        } else if (user.getIdCardStatus() == 1) {
            vo.setAuthStatusString("认证通过");
        } else if (user.getIdCardStatus() == 2) {
            vo.setAuthStatusString("待审核");
        } else if (user.getIdCardStatus() == 3) {
            vo.setAuthStatusString("审核失败");
        }

        return vo;
    }
}
