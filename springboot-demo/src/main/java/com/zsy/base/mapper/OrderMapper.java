package com.zsy.base.mapper;

import com.zsy.base.po.OrderDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    int insertOrder(OrderDo orderDo);

    List<OrderDo> queryList(@Param("pids") List<Integer> pids);

    OrderDo queryUseDollar(@Param("id") Integer id, @Param("addition") String addition);

    OrderDo queryUseHashtag(@Param("id") Integer id, @Param("addition") String addition);
}
