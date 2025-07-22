package com.ccw.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

}
