package com.ccw.order_service.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Long customerId;

    private String customerFirstName;

    private String customerLastName;

    private String customerAddress;

    private String customerEmail;

    private String customerPassword;
}
