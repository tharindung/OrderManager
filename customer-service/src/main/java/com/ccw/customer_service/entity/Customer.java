package com.ccw.customer_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long customerId;

    @Column(name = "cust_fname")
    private String customerFirstName;

    @Column(name = "cust_lname")
    private String customerLastName;

    @Column(name = "cust_address")
    private String customerAddress;

    @Column(name = "cust_email")
    private String customerEmail;

    @Column(name = "cust_password")
    private String customerPassword;
}
