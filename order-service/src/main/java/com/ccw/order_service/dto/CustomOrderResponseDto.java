package com.ccw.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomOrderResponseDto {

    private OrderDto order;

    private CustomerDto customer;

    private StaffDto staff;
}
