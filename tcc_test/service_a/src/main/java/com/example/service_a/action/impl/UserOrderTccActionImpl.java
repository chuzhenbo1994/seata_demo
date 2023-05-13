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
package com.example.service_a.action.impl;


import com.example.service_a.action.UserOrderTccAction;
import com.example.service_a.entity.UserOrder;
import com.example.service_a.mapper.UserOrderMapper;
import com.example.service_a.service.UserOderService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserOrderTccActionImpl implements UserOrderTccAction {
    @Autowired
    private UserOrderMapper orderMapper;

    @Override
    public void geneOrder(UserOrder userOrder, Long id) {
        // 生成订单
        orderMapper.insert(userOrder);
     //   userOderService.save(userOrder);
        log.info("geneOrder---------------------{}", id);
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        Long id = (Long)businessActionContext.getActionContext("id");
        log.info("commit---------------------{}", id);
        return true;
    }

    @Override
    public boolean cancel(BusinessActionContext businessActionContext) {
        Long id = (Long)businessActionContext.getActionContext("id");
        log.info("cancel---------------------{}", id);
        if (id == null) {
            return true;
        }
    //    userOderService.removeById(id);
        int i = orderMapper.deleteById(id);
        return true;
    }
}
