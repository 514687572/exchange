package com.cmd.exchange.admin.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/4.
 */
@Setter
@Getter
public class AuthorityEntity implements Serializable {

    private static final long serialVersionUID = -6458892214527049940L;
    //菜单id
    private Integer resId;
    //菜单名称
    private String name;
    //二级菜单列表
    private List<AuthorityEntity> secondList;

    //三级菜单列表
    private List<AuthorityEntity> thirdList;
}
