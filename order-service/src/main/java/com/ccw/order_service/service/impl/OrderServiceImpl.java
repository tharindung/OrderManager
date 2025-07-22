package com.ccw.order_service.service.impl;

import com.ccw.order_service.dto.OrderDto;
import com.ccw.order_service.entity.Order;
import com.ccw.order_service.entity.Status;
import com.ccw.order_service.exception.ResourceNotFoundException;
import com.ccw.order_service.repository.OrderRepository;
import com.ccw.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        Order order = modelMapper.map(orderDto, Order.class);

        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {

        return orderRepository.findAll().stream().map((order)->modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long orderId) {

        Order foundOrder = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with ID : " + orderId + " does not exist !"));

        return modelMapper.map(foundOrder, OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Long orderId) {

        Order foundOrder = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with ID : " + orderId + " does not exist !"));

        foundOrder.setOrderDate(orderDto.getOrderDate());
        foundOrder.setOrderDesc(orderDto.getOrderDesc());
        foundOrder.setOrderStatus(modelMapper.map(orderDto.getOrderStatus(), Status.class));
        foundOrder.setOrderPrice(orderDto.getOrderPrice());

        Order updatedOrder = orderRepository.save(foundOrder);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public void deleteOrder(Long orderId) {

        orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with ID : " + orderId + " does not exist !"));

        orderRepository.deleteById(orderId);
    }
}
