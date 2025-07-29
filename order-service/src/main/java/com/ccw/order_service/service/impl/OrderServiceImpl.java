package com.ccw.order_service.service.impl;

import com.ccw.order_service.dto.CustomOrderResponseDto;
import com.ccw.order_service.dto.CustomerDto;
import com.ccw.order_service.dto.OrderDto;
import com.ccw.order_service.dto.StaffDto;
import com.ccw.order_service.entity.Order;
import com.ccw.order_service.entity.Status;
import com.ccw.order_service.exception.ResourceNotFoundException;
import com.ccw.order_service.repository.OrderRepository;
import com.ccw.order_service.service.APIClient;
import com.ccw.order_service.service.APIStaffClient;
import com.ccw.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private ModelMapper modelMapper;

    //private RestTemplate restTemplate;

    //private WebClient webClient;

    private APIClient apiClient;

    private APIStaffClient apiStaffClient;

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
    //public OrderDto getOrderById(Long orderId) {
    public CustomOrderResponseDto getOrderById(Long orderId) {

        //Order foundOrder = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with ID : " + orderId + " does not exist !"));
        Order foundOrder = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order", "orderId", orderId));

        /* Rest API call to 'Customer-Service' Microservice - using RestTemplate */

        /*ResponseEntity<CustomerDto> responseEntity = restTemplate.getForEntity("http://localhost:8081/customer-service/apis/customers/" +
                foundOrder.getOrderCustId(), CustomerDto.class);

        CustomerDto customerDto = responseEntity.getBody();*/

        /* Rest API call to 'Customer-Service' Microservice - using WebClient */

        /*CustomerDto customerDto = webClient.get().uri("http://localhost:8081/customer-service/apis/customers/" +
                foundOrder.getOrderCustId())
                .retrieve()
                .bodyToMono(CustomerDto.class)
                .block();
         */

        /* REST API call to 'Customer-Service' Microservice - using FeignClient */

        CustomerDto customerDto = apiClient.getCustomerById(foundOrder.getOrderCustId());

        CustomOrderResponseDto customeOrderResponseDto = new CustomOrderResponseDto();

        customeOrderResponseDto.setOrder(modelMapper.map(foundOrder, OrderDto.class));

        /* REST API call to 'Staff-Service' Microservice - using FeignClient */

        StaffDto staffDto = apiStaffClient.getStaffById(foundOrder.getOrderStaffId());

        customeOrderResponseDto.setCustomer(customerDto);

        customeOrderResponseDto.setStaff(staffDto);

        //return modelMapper.map(foundOrder, OrderDto.class);
        return customeOrderResponseDto;
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Long orderId) {

        //Order foundOrder = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with ID : " + orderId + " does not exist !"));
        Order foundOrder = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order", "orderId", orderId));

        foundOrder.setOrderDate(orderDto.getOrderDate());
        foundOrder.setOrderDesc(orderDto.getOrderDesc());
        foundOrder.setOrderStatus(modelMapper.map(orderDto.getOrderStatus(), Status.class));
        foundOrder.setOrderPrice(orderDto.getOrderPrice());

        Order updatedOrder = orderRepository.save(foundOrder);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public void deleteOrder(Long orderId) {

        //orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with ID : " + orderId + " does not exist !"));
        orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","orderId",orderId));

        orderRepository.deleteById(orderId);
    }
}
