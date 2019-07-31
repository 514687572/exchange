package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.CoinMapper;
import com.cmd.exchange.common.mapper.TransferAddressMapper;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.TransferAddress;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.CoinVO;
import com.github.pagehelper.Page;
import net.bytebuddy.asm.Advice;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    @Autowired
    TransferAddressMapper transferAddressMapper;
    @Autowired
    CoinMapper coinMapper;

    public void addTransferAddress(int userId, String coinname, String name, String address) {
        //防止重复添加（应在数据库添加联合UNIQUE索引，但是考虑牵扯数据库比较多，且此处不会有太大并发，故在此校验）
        int result = transferAddressMapper.countTransferAddress(userId, coinname, address);
        Assert.check(result != 0, ErrorCode.ERR_COIN_ADDRESS_IS_EXISTED);
        transferAddressMapper.add(new TransferAddress().setUserId(userId).setCoinName(coinname).setName(name).setAddress(address));
    }

    public void updateTransferAddress(int userId, int id, String name, String coinName, String address) {
        transferAddressMapper.updateTransferAddress(userId, id, name, coinName, address);
    }

    public void delTransferAddress(int userId, int id) {
        transferAddressMapper.del(userId, id);
    }

    public Page<TransferAddress> getTransferList(int userId, String coinname, int pageNo, int pageSize) {
        return transferAddressMapper.getTransferAddressByUserId(userId, coinname, new RowBounds(pageNo, pageSize));
    }

    //获取
    public List<Coin> getAllCoins() {
        return coinMapper.getCoin();
    }

}
