package com.ccw.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {

    private Long staffId;

    private String staffFirstName;

    private String staffLastName;

    private String staffEmail;

    private String staffPassword;
}
