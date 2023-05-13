package com.example.service_b.controller;/*
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



import com.example.common.vo.Result;
import com.example.service_b.action.CompanyProductAction;
import com.example.service_b.entity.CompanyProduct;
import com.example.service_b.service.ICompanyProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyProductController {

    @Autowired
    private ICompanyProductService service;

    @Autowired
    private CompanyProductAction companyProductAction;

    @GetMapping("/deduct")
    public Result<?> deduct(@RequestParam("id") Long id) {
       if (companyProductAction.deduct(id)){
           return Result.OK();
       };return Result.error("失败");
    }

    @GetMapping("/all")
    public List<CompanyProduct> all(){
        return service.list();
    }
}
