package com.ccw.order_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDto {

    private Integer statusId;

    @NotEmpty(message = "Order Status should not be null or empty !")
    private String status;

    @JsonIgnore
    private Set<OrderDto> orders;
}
