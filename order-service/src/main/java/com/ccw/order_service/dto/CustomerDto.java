package com.ccw.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long customerId;

    private String customerFirstName;

    private String customerLastName;

    private String customerAddress;

    private String customerEmail;

    private String customerPassword;
}
