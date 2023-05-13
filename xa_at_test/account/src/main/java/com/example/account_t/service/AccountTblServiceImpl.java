package com.example.account_t.service;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.account_t.entity.AccountTbl;
import com.example.account_t.mapper.AccountTblMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-05-12
 */
@Service
public class AccountTblServiceImpl extends ServiceImpl<AccountTblMapper, AccountTbl> implements IAccountTblService {

    @Transactional
    @Override
    public void reduce(String userId, int money) {

        UpdateWrapper<AccountTbl> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("money = money - " + money).eq("user_id", userId);
        this.update(null, updateWrapper);



        AccountTbl one = this.lambdaQuery().eq(AccountTbl::getUserId, userId).one();

        if (one.getMoney() < 0) {
            throw new RuntimeException("Not Enough Money ...");
        }
    }

}
