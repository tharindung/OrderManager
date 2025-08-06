package com.ccw.order_service.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomOrderResponseDto {

    private OrderDto order;

    private CustomerDto customer;

    private StaffDto staff;
}
