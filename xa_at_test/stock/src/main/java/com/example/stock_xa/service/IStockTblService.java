package com.example.stock_xa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.stock_xa.entity.StockTbl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-05-12
 */
public interface IStockTblService extends IService<StockTbl> {

    void deduct(String commodityCode, int count);
}
