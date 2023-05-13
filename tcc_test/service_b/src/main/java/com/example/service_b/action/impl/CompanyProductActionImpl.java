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
package com.example.service_b.action.impl;


import com.example.service_b.action.CompanyProductAction;
import com.example.service_b.entity.CompanyProduct;
import com.example.service_b.service.ICompanyProductService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 这段代码是TCC事务中的实现，其中deduct方法是try阶段执行的，用于扣减产品库存。
 * 如果tcc事务提交成功，commit方法将被调用，用于提交库存扣减操作；
 * 否则，如果tcc事务回滚，cancel方法将被调用，用于回滚库存扣减操作。
 * 在本例中，commit方法中不需要执行任何实际操作，因为产品扣减操作已经在try阶段完成。
 * cancel方法中需要将之前扣减的库存回滚，即将库存加回来。
 */
@Component
@Slf4j
public class CompanyProductActionImpl implements CompanyProductAction {
    @Autowired
    private ICompanyProductService companyProductService;

    @Override
    @Transactional
    public boolean deduct(Long id) {
        CompanyProduct id1 = companyProductService.query().eq("id", id).one();
        id1.setAccount(id1.getAccount() - 1);
       return companyProductService.updateById(id1);
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        Long id = Long.parseLong(businessActionContext.getActionContext("id") + "");
        log.info("cancel---------------------{}", id);
        return true;
    }

    @Override
    public boolean cancel(BusinessActionContext businessActionContext) {
        Long id = Long.parseLong(businessActionContext.getActionContext("id") + "");
        log.info("cancel---------------------{}", id);
        CompanyProduct id1 = companyProductService.query().eq("id", id).one();
        id1.setAccount(id1.getAccount() + 1);
        companyProductService.updateById(id1);
        return true;
    }
}
