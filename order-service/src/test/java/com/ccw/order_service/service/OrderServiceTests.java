package com.ccw.order_service.service;

import com.ccw.order_service.dto.*;
import com.ccw.order_service.entity.Order;
import com.ccw.order_service.entity.Status;
import com.ccw.order_service.exception.ResourceNotFoundException;
import com.ccw.order_service.repository.OrderRepository;
import com.ccw.order_service.service.impl.OrderServiceImpl;
import net.bytebuddy.NamingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private ModelMapper modelMapper;

    private Order order;

    private OrderDto orderDto;

    private Status status;

    private StatusDto statusDto;

    private CustomerDto customerDto;

    private StaffDto staffDto;

    @Mock
    private APIClient apiClient;

    @Mock
    private APIStaffClient apiStaffClient;

    @BeforeEach
    public void setup()
    {
        status = Status.builder()
                .statusId(1)
                .status("Assigned").build();

        statusDto = StatusDto.builder()
                .statusId(1)
                .status("Assigned").build();

        order = Order.builder()
                .orderId(1L)
                .orderDate(LocalDate.parse("2025-07-25"))
                .orderPrice(1250.00)
                .orderStatus(status)
                .orderCustId(1L)
                .orderStaffId(1L).build();

        orderDto = OrderDto.builder()
                .orderId(1L)
                .orderDate(LocalDate.parse("2025-07-25"))
                .orderPrice(1250.00)
                .orderStatus(statusDto)
                .orderCustId(1L)
                .orderStaffId(1L).build();

        customerDto = CustomerDto.builder()
                .customerId(1L)
                .customerFirstName("Rob")
                .customerLastName("David")
                .customerEmail("rob.david@gmail.com")
                .customerPassword("12345").build();

        staffDto = StaffDto.builder()
                .staffId(1L)
                .staffFirstName("Amanda")
                .staffLastName("Smith")
                .staffEmail("amanda.smith@gmail.com")
                .staffPassword("12345").build();

    }

    //Junit test for createOder method
    @DisplayName("Junit test for createOder method")
    @Test
    public void givenOrderDtoObject_whenCreateOrder_thenReturnSavedOrderDtoObject()
    {
        //Given - precondition
        given(orderRepository.save(order)).willReturn(order);

        doReturn(order).when(modelMapper).map(orderDto, Order.class);

        doReturn(orderDto).when(modelMapper).map(order, OrderDto.class);

        //When - behavior we are testing
        OrderDto savedOrder = orderService.createOrder(orderDto);

        //Then - verifying the output
        assertThat(savedOrder).isNotNull();
    }

    //Junit test for getAllOrders method - positive scenario
    @DisplayName("Junit test for getAllOrders method - positive scenario")
    @Test
    public void givenOrderList_whenGetAllOrders_thenReturnOrderDtoList()
    {
        //Given - precondition
        Order order1 = Order.builder()
                .orderDate(LocalDate.parse("2025-07-26"))
                .orderPrice(2500.00)
                .orderStatus(status)
                .orderCustId(2L)
                .orderStaffId(2L).build();

        given(orderRepository.findAll()).willReturn(List.of(order, order1));

        //When - behavior we are testing
        List<OrderDto> allOrders = orderService.getAllOrders();

        //Then - verifying the output
        assertThat(allOrders).isNotNull();
        assertThat(allOrders.size()).isEqualTo(2);
    }

    //Junit test for getAllOrders method - negative scenario
    @DisplayName("Junit test for getAllOrders method - negative scenario")
    @Test
    public void givenEmptyOrderList_whenGetAllOrders_thenReturnEmptyOrderDtoList()
    {
        //Given - precondition
        given(orderRepository.findAll()).willReturn(Collections.emptyList());

        //When - behavior we are testing
        List<OrderDto> allOrders = orderService.getAllOrders();

        //Then - verifying the output
        assertThat(allOrders).isEmpty();
        assertThat(allOrders.size()).isEqualTo(0);
    }

    //Junit test for getOrderById method - positive scenario
    @DisplayName("Junit test for getOrderById method - positive scenario")
    @Test
    public void givenOrderId_whenGetOrderById_thenReturnOrderDtoObject()
    {
        //Given - precondition
        given(orderRepository.findById(order.getOrderId())).willReturn(Optional.of(order));

        doReturn(orderDto).when(modelMapper).map(order, OrderDto.class);

        given(apiClient.getCustomerById(orderDto.getOrderCustId())).willReturn(customerDto);

        given(apiStaffClient.getStaffById(orderDto.getOrderStaffId())).willReturn(staffDto);

        //When - behavior we are testing
        CustomOrderResponseDto returnedCustomResponse = orderService.getOrderById(order.getOrderId());

        //Then - verifying the output
        assertThat(returnedCustomResponse).isNotNull();
    }

    //Junit test for getOrderById method - negative scenario
    @DisplayName("Junit test for getOrderById method - negative scenario")
    @Test
    public void givenInvalidOrderId_whenGetOrderById_thenReturnResourceNotFoundException()
    {
        //Given - precondition
        Long invalidOrderId = 99L;

        given(orderRepository.findById(invalidOrderId)).willThrow(new ResourceNotFoundException("Order", "orderId", invalidOrderId));

        //When - behavior we are testing & Then - verifying the output
        assertThatThrownBy(()->{
            orderService.getOrderById(invalidOrderId);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order not found with orderId : 99");
    }

    //Junit test for updateOrder method
    @DisplayName("Junit test for updateOrder method")
    @Test
    public void givenOrderDtoObject_whenUpdateOrder_thenReturnUpdatedOrderDtoObject()
    {
        //Given - precondition
        given(orderRepository.findById(order.getOrderId())).willReturn(Optional.of(order));

        given(orderRepository.save(order)).willReturn(order);

        doReturn(orderDto).when(modelMapper).map(order, OrderDto.class);

        doReturn(status).when(modelMapper).map(statusDto, Status.class);

        order.setOrderPrice(2500.00);

        orderDto.setOrderPrice(2500.00);

        //When - behavior we are testing
        OrderDto updatedOrder = orderService.updateOrder(orderDto, orderDto.getOrderId());

        //Then - verifying the output
        assertThat(updatedOrder.getOrderPrice()).isEqualTo(2500.00);
    }

    //Junit test for deleteOrder method
    @DisplayName("Junit test for deleteOrder method")
    @Test
    public void givenOrderId_whenDeleteOrder_thenReturnNothing()
    {
        //Given - precondition
        given(orderRepository.findById(order.getOrderId())).willReturn(Optional.of(order));

        willDoNothing().given(orderRepository).deleteById(order.getOrderId());

        //When - behavior we are testing
        orderService.deleteOrder(order.getOrderId());

        //Then - verifying the output
        verify(orderRepository, times(1)).deleteById(order.getOrderId());
    }
}
