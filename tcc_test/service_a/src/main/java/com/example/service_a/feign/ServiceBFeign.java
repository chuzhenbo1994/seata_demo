package com.example.service_a.feign;

import com.example.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name="server-b")
public interface ServiceBFeign {

    @GetMapping(value="/deduct",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<?> deduct(@RequestParam("id") Long id);

}
