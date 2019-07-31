package com.cmd.exchange.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NationalCode implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String nationalName;
    private String chineseName;
    private String abbre;
    private String code;
}
