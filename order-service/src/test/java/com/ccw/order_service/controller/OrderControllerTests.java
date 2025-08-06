package com.ccw.order_service.controller;

import com.ccw.order_service.dto.*;
import com.ccw.order_service.exception.ResourceNotFoundException;
import com.ccw.order_service.service.OrderService;
import com.ccw.order_service.service.StatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@WebMvcTest
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private StatusService statusService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDto orderDto;

    private StatusDto statusDto;

    @BeforeEach
    public void setup()
    {
        statusDto = StatusDto.builder()
                .status("Assigned").build();

        orderDto = OrderDto.builder()
                .orderDate(LocalDate.parse("2025-07-27"))
                .orderStatus(statusDto)
                .orderPrice(1500.00)
                .orderCustId(1L)
                .orderStaffId(1L).build();
    }

    //Junit test for createOrder REST API
    @DisplayName("Junit test for createOrder REST API")
    @Test
    public void givenOrderDtoObject_whenCreateOrder_thenReturnSavedOrderDtoObject() throws Exception
    {
        //Given - precondition

        /* Stubbing */
        given(orderService.createOrder(ArgumentMatchers.any(OrderDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/order-service/apis/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)));

        //Then - verifying the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate", CoreMatchers.is(orderDto.getOrderDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderPrice", CoreMatchers.is(orderDto.getOrderPrice())));
    }

    //Junit test for getAllOrders REST API - positive scenario
    @DisplayName("Junit test for getAllOrders - positive scenario")
    @Test
    public void givenOrderDtoList_whenGetAllOrders_thenReturnOrderDtoList() throws Exception
    {
        //Given - precondition
        OrderDto orderDto1 = OrderDto.builder()
                .orderDate(LocalDate.parse("2025-08-01"))
                .orderPrice(500.00)
                .orderStatus(statusDto)
                .orderCustId(2L)
                .orderStaffId(2L).build();

        List<OrderDto> allOrders = new ArrayList<>();
        allOrders.add(orderDto);
        allOrders.add(orderDto1);

        /* Stubbing */
        given(orderService.getAllOrders()).willReturn(allOrders);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/apis/orders"));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(allOrders.size())));
    }

    //Junit test for getAllOrders REST API - negative scenario
    @DisplayName("Junit test for getAllOrders - negative scenario")
    @Test
    public void givenEmptyOrderDtoList_whenGetAllOrders_thenReturnEmptyOrderDtoList() throws Exception
    {
        //Given - precondition
        List<OrderDto> allOrders = new ArrayList<>();

        /* Stubbing */
        given(orderService.getAllOrders()).willReturn(Collections.emptyList());

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/apis/orders"));

        //Then - verifying the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(allOrders.size())));
    }

    //Junit test for getOrderById REST API - positive scenario
    @DisplayName("Junit test for getOrderById REST API - positive scenario")
    @Test
    public void givenOrderId_whenGetOrderById_thenReturnOrderDtoObject() throws Exception
    {
        //Given - precondition
        Long orderId = 1L;

        StaffDto staffDto = StaffDto.builder()
                .staffFirstName("Rob")
                .staffLastName("David")
                .staffEmail("rob.david@gmail.com")
                .staffPassword("12345").build();

        CustomerDto customerDto = CustomerDto.builder()
                .customerFirstName("Anna")
                .customerLastName("Doe")
                .customerEmail("anna.doe@gmail.com")
                .customerPassword("56789").build();

        CustomOrderResponseDto customResponseDto = CustomOrderResponseDto.builder()
                .order(orderDto)
                .staff(staffDto)
                .customer(customerDto).build();

        /* Stubbing */
        given(orderService.getOrderById(orderId)).willReturn(customResponseDto);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/apis/orders/{id}", orderId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.order.orderDate", CoreMatchers.is(orderDto.getOrderDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.order.orderPrice", CoreMatchers.is(orderDto.getOrderPrice())));
    }

    //Junit test for getOrderById REST API - negative scenario
    @DisplayName("Junit test for getOrderById REST API - negative scenario")
    @Test
    public void givenInvalidOrderId_whenGetOrderById_thenReturnResourceNotFoundException() throws Exception
    {
        //Given - precondition
        Long invalidOrderId = 99L;

        /* Stubbing */
        given(orderService.getOrderById(invalidOrderId)).willThrow(new ResourceNotFoundException("Order", "orderId", invalidOrderId));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/apis/orders/{id}", invalidOrderId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    //Junit test for updateOrder REST API
    @DisplayName("Junit test for updateOrder REST API")
    @Test
    public void givenOrderDtoObject_whenUpdateOrder_thenReturnUpdatedOrderDtoObject() throws Exception
    {
        //Given - precondition
        Long orderId = 1L;

        StaffDto staffDto = StaffDto.builder()
                .staffFirstName("Rob")
                .staffLastName("David")
                .staffEmail("rob.david@gmail.com")
                .staffPassword("12345").build();

        CustomerDto customerDto = CustomerDto.builder()
                .customerFirstName("Anna")
                .customerLastName("Doe")
                .customerEmail("anna.doe@gmail.com")
                .customerPassword("56789").build();

        CustomOrderResponseDto customResponseDto = CustomOrderResponseDto.builder()
                .order(orderDto)
                .staff(staffDto)
                .customer(customerDto).build();

        OrderDto updatedOrderDto = OrderDto.builder()
                .orderDate(LocalDate.parse("2025-08-05")) //updated
                .orderStatus(statusDto)
                .orderPrice(2500.00) //updated
                .orderCustId(1L)
                .orderStaffId(1L).build();

        /* Stubbing */
        given(orderService.getOrderById(orderId)).willReturn(customResponseDto);

        /* Stubbing */
        given(orderService.updateOrder(ArgumentMatchers.any(OrderDto.class), ArgumentMatchers.any(Long.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/order-service/apis/orders/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedOrderDto)));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate", CoreMatchers.is(updatedOrderDto.getOrderDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderPrice", CoreMatchers.is(updatedOrderDto.getOrderPrice())));

    }

    //Junit test for deleteOrder REST API
    @DisplayName("Junit test for deleteOrder REST API")
    @Test
    public void givenOrderId_whenDeleteOrder_thenReturn200() throws Exception
    {
        //Given - precondition
        Long orderId = 1L;

        /* Stubbing */
        willDoNothing().given(orderService).deleteOrder(orderId);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/order-service/apis/orders/{id}", orderId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
