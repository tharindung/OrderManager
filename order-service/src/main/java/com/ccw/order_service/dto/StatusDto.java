package com.ccw.order_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {

    private Integer statusId;

    @NotEmpty(message = "Order Status should not be null or empty !")
    private String status;

    @JsonIgnore
    private Set<OrderDto> orders;
}
