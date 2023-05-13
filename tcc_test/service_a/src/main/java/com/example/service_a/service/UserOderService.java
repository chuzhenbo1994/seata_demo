package com.example.service_a.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.service_a.entity.UserOrder;

public interface UserOderService extends IService<UserOrder> {
    void geneOrder(UserOrder userOrder);

}
