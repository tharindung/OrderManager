package com.ccw.order_service.controller;


import com.ccw.order_service.dto.CustomOrderResponseDto;
import com.ccw.order_service.dto.OrderDto;
import com.ccw.order_service.entity.Order;
import com.ccw.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/order-service/apis/orders")
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto)
    {
        OrderDto savedOrder = orderService.createOrder(orderDto);

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders()
    {
        List<OrderDto> allOrders = orderService.getAllOrders();

        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @GetMapping("{id}")
    //public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long orderId)
    public ResponseEntity<CustomOrderResponseDto> getOrderById(@PathVariable("id") Long orderId)
    {
        //OrderDto foundOrder = orderService.getOrderById(orderId);
        CustomOrderResponseDto customOrderResponseDto = orderService.getOrderById(orderId);

        //return new ResponseEntity<>(foundOrder, HttpStatus.OK);
        return new ResponseEntity<>(customOrderResponseDto, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto, @PathVariable("id") Long orderId)
    {
        OrderDto updatedOrder = orderService.updateOrder(orderDto, orderId);

        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long orderId)
    {
        orderService.deleteOrder(orderId);

        return new ResponseEntity<>("Order with ID : " + orderId + " deleted successfully !", HttpStatus.OK);
    }
}
