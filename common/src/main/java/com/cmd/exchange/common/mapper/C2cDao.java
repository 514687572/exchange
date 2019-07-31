package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.vo.C2cApplicationVO;
import com.cmd.exchange.common.vo.C2cOrderDetailVO;
import com.cmd.exchange.common.vo.C2cOrderVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

@Mapper
public interface C2cDao {

    Page<C2cApplicationVO> getC2cApplications(@Param("record") C2cApplicationVO record, RowBounds rowBounds);

    List<C2cApplicationVO> downloadApplications(@Param("record") C2cApplicationVO record, @Param("begin") Date begin, @Param("end") Date end);

    int cancelApplication(Long id);


    Page<C2cOrderVO> getC2cOrders(@Param("record") C2cOrderVO record, RowBounds rowBounds);

    List<C2cOrderVO> downloadOrders(@Param("record") C2cOrderVO record, @Param("begin") Date begin, @Param("end") Date end);

    C2cOrderDetailVO getC2cOrderDetail(@Param("id") Long id);
}
