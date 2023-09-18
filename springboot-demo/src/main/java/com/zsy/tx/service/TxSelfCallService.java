package com.zsy.tx.service;

import com.zsy.base.po.OrderDo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Allenzsy
 * @Date 2022/8/9 22:43
 * @Description:
 */
@Service
public interface TxSelfCallService {

    public int insertOrder(OrderDo orderDo);

    public int insertListSelfCallInvalid(List<OrderDo> orderDoList);

    public int insertListSelfCallCorrect(List<OrderDo> orderDoList);

}
