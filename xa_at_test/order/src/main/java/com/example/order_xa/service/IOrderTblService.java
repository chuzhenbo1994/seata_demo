package com.example.order_xa.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.order_xa.entity.OrderTbl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-05-12
 */
public interface IOrderTblService extends IService<OrderTbl> {

    void create(String userId, String commodityCode, Integer count);
}
