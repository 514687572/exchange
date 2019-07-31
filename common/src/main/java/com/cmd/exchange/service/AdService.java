package com.cmd.exchange.service;


import com.cmd.exchange.common.enums.AdvertisementStatus;
import com.cmd.exchange.common.mapper.AdMapper;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.AdResVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */
@Service("adService")
public class AdService {

    @Autowired
    private AdMapper adDao;

    public void add(AdResVO adResVO) {
        int count = adDao.add(adResVO);
        Assert.check(count < 1, 1, "添加广告失败");
    }

    public Page<AdResVO> getADList(String startTime, String endTime, Integer pageNo, Integer pageSize) {
        Page<AdResVO> list = adDao.getADList(startTime, endTime, new RowBounds(pageNo, pageSize));
        return list;
    }

    public void updateStatus(AdvertisementStatus status, Integer id) {
        Assert.check(adDao.getAdById(id) == null, 1, "此条广告不存在");
        int count = adDao.updateStatus(status.getValue(), id);
        Assert.check(count < 1, 1, "更新失败");
    }

    public void delById(Integer id) {
        Assert.check(adDao.getAdById(id) == null, 1, "此条广告不存在");
        AdResVO adResVO = adDao.getAdById(id);
        Assert.check(adResVO.getStatus() == AdvertisementStatus.SHOW, 1, "禁止删除已上线的广告！");
        int count = adDao.delById(id);
        Assert.check(count < 1, 1, "删除失败");
    }

    /**
     * 批量删除广告
     *
     * @param ids
     */
    public void delAdvertiseBatch(Integer[] ids) {
        for (Integer id : ids) {
            delById(id);
        }
    }

    public void updateADInfo(AdResVO adResVO) {
        int count = adDao.updateADInfo(adResVO);
        Assert.check(count < 1, 1, "更新失败");
    }

    public List<AdResVO> queryADList(String locale, int clientType) {
        return adDao.queryADList(locale, clientType);
    }


    public AdResVO getAdDetail(Integer adId) {
        return adDao.getAdById(adId);
    }

}
