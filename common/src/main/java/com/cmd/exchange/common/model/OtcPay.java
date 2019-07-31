package com.cmd.exchange.common.model;

import com.cmd.exchange.blockchain.bitcoin.BitcoindRpcClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OtcPay implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private Integer userId;
    private Integer bankType;
    private String bankName;
    private String bankUser;
    private String bankNo;
    private Long orderId;
    private Date createTime;
}
