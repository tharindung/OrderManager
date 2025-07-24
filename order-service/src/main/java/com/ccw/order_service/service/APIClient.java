package com.ccw.order_service.service;

import com.ccw.order_service.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(url="http://localhost:8081", value="CUSTOMER-SERVICE")
@FeignClient(name="CUSTOMER-SERVICE")
public interface APIClient {

    @GetMapping("/customer-service/apis/customers/{id}")
    CustomerDto getCustomerById(@PathVariable("id") Long customerId);
}
