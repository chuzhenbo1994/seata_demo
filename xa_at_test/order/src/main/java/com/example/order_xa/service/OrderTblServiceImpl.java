package com.example.order_xa.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order_xa.entity.OrderTbl;
import com.example.order_xa.feign.AccountFeignClient;
import com.example.order_xa.mapper.OrderTblMapper;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderTblServiceImpl extends ServiceImpl<OrderTblMapper, OrderTbl> implements IOrderTblService {

    @Autowired
    private AccountFeignClient accountFeignClient;

    @Override
    public void create(String userId, String commodityCode, Integer count) {
        String xid = RootContext.getXID();
        log.info("create order in transaction: " + xid);

        // 定单总价 = 订购数量(count) * 商品单价(100)
        int orderMoney = count * 100;
        OrderTbl orderTbl = new OrderTbl();
        orderTbl.setUserId(userId);
        orderTbl.setCommodityCode(commodityCode);
        orderTbl.setCount(count);
        orderTbl.setMoney(orderMoney);

        boolean save = this.save(orderTbl);

        String result = accountFeignClient.reduce(userId, orderMoney);
        if (!"success".equals(result)) {
            throw new RuntimeException("Failed to call Account Service. ");
        }
    }

}
