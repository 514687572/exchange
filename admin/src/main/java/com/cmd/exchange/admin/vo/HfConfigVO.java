package com.cmd.exchange.admin.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class HfConfigVO implements Serializable {

    private Integer buyCount;

    private Integer sellCount;
}
