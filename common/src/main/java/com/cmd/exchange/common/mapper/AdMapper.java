package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.vo.AdResVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */
@Mapper
public interface AdMapper {

    /**
     * 添加广告
     *
     * @param adResVO
     * @return
     */
    int add(AdResVO adResVO);

    /**
     * 查询广告列表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Page<AdResVO> getADList(@Param("startDate") String startDate,
                            @Param("endDate") String endDate,
                            RowBounds rowBounds);

    /**
     * 查询广告列表长度
     *
     * @param startDate
     * @param endDate
     * @return
     */
    int getADCount(@Param("startDate") String startDate,
                   @Param("endDate") String endDate);

    /**
     * 更新广告状态
     *
     * @param status
     * @param id
     * @return
     */
    int updateStatus(@Param("status") Integer status, @Param("id") Integer id);

    /**
     * 查询广告的详细信息
     *
     * @param id
     * @return
     */
    AdResVO getAdById(@Param("id") Integer id);

    /**
     * 删除广告信息
     *
     * @param id
     * @return
     */
    int delById(@Param("id") Integer id);

    /**
     * 更新广告信息
     *
     * @param adResVO
     * @return
     */
    int updateADInfo(AdResVO adResVO);

    /**
     * 获取商圈首页轮播图
     *
     * @return
     */
    List<AdResVO> queryADList(@Param("locale") String locale, @Param("clientType") Integer clientType);

}
