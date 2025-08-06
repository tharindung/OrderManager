package com.ccw.staff_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffDto {

    private Long staffId;

    @NotEmpty(message = "Staff First Name should not be null or empty !")
    private String staffFirstName;

    @NotEmpty(message = "Staff Last Name should not be null or empty !")
    private String staffLastName;

    @NotEmpty(message = "Staff Email should not be null or empty !")
    @Email(message = "Email should be a valid email !")
    private String staffEmail;

    @NotEmpty(message = "Password should not be null or empty !")
    private String staffPassword;
}
