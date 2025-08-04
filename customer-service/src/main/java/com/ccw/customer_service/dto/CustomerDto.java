package com.ccw.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Long customerId;

    @NotEmpty(message = "Customer First Name should not be null or empty !")
    private String customerFirstName;

    @NotEmpty(message = "Customer Last Name should not be null or empty !")
    private String customerLastName;

    private String customerAddress;

    @NotEmpty(message = "Customer Email should not be null or empty !")
    @Email(message = "Email Address should be valid !")
    private String customerEmail;

    @NotEmpty(message = "Customer Password should not be null or empty !")
    private String customerPassword;
}
