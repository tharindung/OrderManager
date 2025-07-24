package com.ccw.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long orderId;

    private LocalDate orderDate;

    private String orderDesc;

    private StatusDto orderStatus;

    private Double orderPrice;

    //Need to associate to 'Customer' entity
    private Long orderCustId;
}
