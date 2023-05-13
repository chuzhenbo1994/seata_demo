package com.example.service_a.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.common.vo.Result;
import com.example.service_a.action.UserOrderTccAction;
import com.example.service_a.entity.UserOrder;
import com.example.service_a.feign.ServiceBFeign;
import com.example.service_a.mapper.UserOrderMapper;
import com.example.service_a.service.UserOderService;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserOderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOderService {
    @Autowired
    private UserOrderTccAction userOrderTccAction;

    @Autowired
    @Resource
    private ServiceBFeign serviceBFeign;

    @Override
    @GlobalTransactional
    @Transactional
    public void geneOrder(UserOrder userOrder) {
        // 扣减库存
        Result<?> deduct = serviceBFeign.deduct(1L);

        if (deduct.getCode() != 200){
            throw new RuntimeException("扣减库存失败！");
        }
        // 生成订单
        long id = IdWorker.getId();
        userOrder.setId(id);
        userOrderTccAction.geneOrder(userOrder, id);
        // 校验 事务回滚
        int a = 1 / 0;
    }
}