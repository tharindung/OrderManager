package com.ccw.order_service.service;

import com.ccw.order_service.dto.StaffDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "STAFF-SERVICE")
public interface APIStaffClient {

    @GetMapping("/staff-service/apis/staff/{id}")
    StaffDto getStaffById(@PathVariable("id") Long staffId);
}
