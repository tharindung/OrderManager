package com.ccw.staff_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "staff_fname")
    private String staffFirstName;

    @Column(name = "staff_lname")
    private String staffLastName;

    @Column(name = "staff_email")
    private String staffEmail;

    @Column(name = "staff_password")
    private String staffPassword;
}
