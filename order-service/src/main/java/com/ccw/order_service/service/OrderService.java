package com.ccw.order_service.service;

import com.ccw.order_service.dto.OrderDto;

import java.util.List;

public interface OrderService {

    public OrderDto createOrder(OrderDto orderDto);

    public List<OrderDto> getAllOrders();

    public OrderDto getOrderById(Long orderId);

    public OrderDto updateOrder(OrderDto orderDto, Long orderId);

    public void deleteOrder(Long orderId);
}
