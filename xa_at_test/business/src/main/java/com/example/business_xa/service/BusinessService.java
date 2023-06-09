/*
 *  Copyright 1999-2021 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.example.business_xa.service;

import java.util.Map;


import com.example.business_xa.feign.OrderFeignClient;
import com.example.business_xa.feign.StockFeignClient;
import com.example.common.vo.TestDatas;
import io.seata.core.context.RootContext;

import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessService.class);

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    @Autowired
    private StockFeignClient stockFeignClient;
    @Autowired
    private OrderFeignClient orderFeignClient;

    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount, boolean rollback) {
        String xid = RootContext.getXID();
        LOGGER.info("New Transaction Begins: " + xid);

        String result = stockFeignClient.deduct(commodityCode, orderCount);

        if (!SUCCESS.equals(result)) {
            throw new RuntimeException("库存服务调用失败,事务回滚!");
        }

        result = orderFeignClient.create(userId, commodityCode, orderCount);

        if (!SUCCESS.equals(result)) {
            throw new RuntimeException("订单服务调用失败,事务回滚!");
        }

        if (rollback) {
            throw new RuntimeException("Force rollback ... ");
        }
    }

//    @PostConstruct
//    public void initData() {
//        jdbcTemplate.update("delete from account_tbl");
//        jdbcTemplate.update("delete from order_tbl");
//        jdbcTemplate.update("delete from stock_tbl");
//        jdbcTemplate.update("insert into account_tbl(user_id,money) values('" + TestDatas.USER_ID + "','10000') ");
//        jdbcTemplate.update(
//            "insert into stock_tbl(commodity_code,count) values('" + TestDatas.COMMODITY_CODE + "','100') ");
//    }

//    public boolean validData(String userId, String commodityCode) {
//        Map accountMap = jdbcTemplate.queryForMap("select * from account_tbl where user_id='" + userId + "'");
//        if (Integer.parseInt(accountMap.get("money").toString()) < 0) {
//            // 余额被扣减为负：余额不足
//            return false;
//        }
//
//        Map stockMap = jdbcTemplate.queryForMap("select * from stock_tbl where commodity_code='" + commodityCode + "'");
//        if (Integer.parseInt(stockMap.get("count").toString()) < 0) {
//            // 库存被扣减为负：库存不足
//            return false;
//        }
//
//        return true;
//    }
}
