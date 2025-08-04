package com.ccw.order_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long orderId;

    @NotNull(message = "Order Date should not be null or empty !")
    private LocalDate orderDate;

    private String orderDesc;

    @NotNull(message = "Order Status should not be null or empty !")
    private StatusDto orderStatus;

    @NotNull(message = "Order Price should not be null or empty !")
    private Double orderPrice;

    //Need to associate to 'Customer' entity
    @NotNull(message = "Order Customer should not be null or empty !")
    private Long orderCustId;

    //Need to associate to 'Staff' entity
    private Long orderStaffId;
}
