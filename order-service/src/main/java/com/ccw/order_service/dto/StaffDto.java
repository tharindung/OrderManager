package com.ccw.order_service.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffDto {

    private Long staffId;

    private String staffFirstName;

    private String staffLastName;

    private String staffEmail;

    private String staffPassword;
}
