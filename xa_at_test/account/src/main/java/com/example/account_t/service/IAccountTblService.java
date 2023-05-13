package com.example.account_t.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.account_t.entity.AccountTbl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-05-12
 */
public interface IAccountTblService extends IService<AccountTbl> {

    void reduce(String userId, int money);
}
