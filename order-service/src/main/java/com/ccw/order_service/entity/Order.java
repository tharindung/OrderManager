package com.ccw.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "order_desc")
    private String orderDesc;

    //Need to associate 'Status' entity
    //@ManyToOne(cascade = CascadeType.MERGE)
    @ManyToOne
    @JoinColumn(name="order_status", referencedColumnName = "status_id", nullable = false)
    private Status orderStatus;

    @Column(name = "order_price")
    private Double orderPrice;

    //Need to associate to 'Customer' entity
    @Column(name = "order_cust_id")
    private Long orderCustId;

    //Need to associate to 'Staff' entity
    @Column(name = "order_staff_id")
    private Long orderStaffId;

}
