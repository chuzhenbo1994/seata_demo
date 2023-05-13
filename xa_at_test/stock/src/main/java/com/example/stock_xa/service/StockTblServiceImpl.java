package com.example.stock_xa.service;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.stock_xa.entity.StockTbl;
import com.example.stock_xa.mapper.StockTblMapper;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-05-12
 */
@Service
@Slf4j
public class StockTblServiceImpl extends ServiceImpl<StockTblMapper, StockTbl> implements IStockTblService {


    @Override
    public void deduct(String commodityCode, int count) {
        String xid = RootContext.getXID();
        log.info("deduct stock balance in transaction: " + xid);



        UpdateWrapper<StockTbl> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("count = count - " + count).eq("commodity_code", commodityCode);
        this.update(null, updateWrapper);



    }
}
