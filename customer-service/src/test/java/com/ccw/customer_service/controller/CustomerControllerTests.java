package com.ccw.customer_service.controller;

import com.ccw.customer_service.dto.CustomerDto;
import com.ccw.customer_service.exception.ResourceNotFoundException;
import com.ccw.customer_service.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
/*import org.junit.jupiter.api.extension.MediaType;*/
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@WebMvcTest
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto customerDto;

    @BeforeEach
    public void setup()
    {
        customerDto = CustomerDto.builder()
                .customerFirstName("Mark")
                .customerLastName("Davis")
                .customerEmail("mark.davis@gmail.com")
                .customerPassword("12345").build();
    }

    //Junit test for createCustomer REST API
    @DisplayName("Junit test for createCustomer REST API")
    @Test
    public void givenCustomerDtoObject_whenCreateCustomer_thenReturnSavedCustomerDto() throws Exception
    {
        //Given - precondition

        /* Stubbing */
        given(customerService.createCustomer(ArgumentMatchers.any(CustomerDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/customer-service/apis/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)));

        //Then - verifying the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerFirstName", CoreMatchers.is(customerDto.getCustomerFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerLastName", CoreMatchers.is(customerDto.getCustomerLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerEmail", CoreMatchers.is(customerDto.getCustomerEmail())));
    }

    //Junit test for getAllCustomers REST API - positive scenario
    @DisplayName("Junit test for getAllCustomers REST API - positive scenario")
    @Test
    public void givenCustomerDtoList_whenGetAllCustomers_thenReturnCustomerDtoList() throws Exception
    {
        //Given - precondition
        CustomerDto customerDto1 = CustomerDto.builder()
                                    .customerFirstName("Jane")
                                    .customerLastName("Doe")
                                    .customerEmail("jane.doe@gmail.com")
                                    .customerPassword("12345").build();

        List<CustomerDto> listOfCustomers = new ArrayList<>();
        listOfCustomers.add(customerDto);
        listOfCustomers.add(customerDto1);

        /* Stubbing */
        given(customerService.getAllCustomers()).willReturn(listOfCustomers);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/customer-service/apis/customers"));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfCustomers.size())));
    }

    //Junit test for getAllCustomers REST API - negative scenario
    @DisplayName("Junit test for getAllCustomers REST API - negative scenario")
    @Test
    public void givenEmptyCustomerDtoList_whenGetAllCustomers_thenReturnEmptyCustomerDtoList() throws Exception
    {
        //Given - precondition
        List<CustomerDto> listOfCustomers = new ArrayList<>();

        /* Stubbing */
        given(customerService.getAllCustomers()).willReturn(Collections.emptyList());

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/customer-service/apis/customers"));

        //Then - verifying the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfCustomers.size())));
    }

    //Junit test for getCustomerById REST API - positive scenario
    @DisplayName("Junit test for getCustomerById REST API - positive scenario")
    @Test
    public void givenCustomerId_whenGetCustomerById_thenReturnCustomerDtoObject() throws Exception
    {
        //Given - precondition
        Long customerId = 1L;

        /* Stubbing */
        given(customerService.getCustomerById(customerId)).willReturn(customerDto);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/customer-service/apis/customers/{id}", customerId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerFirstName", CoreMatchers.is(customerDto.getCustomerFirstName())));
    }

    //Junit test for getCustomerById REST API - negative scenario
    @DisplayName("Junit test for getCustomerById REST API - negative scenario")
    @Test
    public void givenInvalidCustomerId_whenGetCustomerById_thenReturnResourceNotFoundException() throws Exception
    {
        //Given - precondition
        Long invalidCustomerId = 99L;

        /* Stubbing */
        given(customerService.getCustomerById(invalidCustomerId)).willThrow(new ResourceNotFoundException("Customer", "customerId", invalidCustomerId));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/customer-service/apis/customers/{id}", invalidCustomerId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    //Junit test for updateCustomer REST API
    @DisplayName("Junit test for updateCustomer REST API")
    @Test
    public void givenCustomerDtoObject_whenUpdateCustomer_thenReturnUpdatedCustomerDtoObject() throws Exception
    {
        //Given - precondition
        Long customerId = 1L;

        CustomerDto updatedCustomerDto = CustomerDto.builder()
                .customerFirstName("Rob")
                .customerLastName("David")
                .customerEmail("rob.david@gmail.com")
                .customerPassword("56789").build();

        /* Stubbing */
        given(customerService.getCustomerById(customerId)).willReturn(customerDto);

        /* Stubbing */
        given(customerService.updateCustomer(ArgumentMatchers.any(CustomerDto.class), ArgumentMatchers.any(Long.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/customer-service/apis/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomerDto)));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerFirstName", CoreMatchers.is(updatedCustomerDto.getCustomerFirstName())));
    }

    //Junit test for deleteCustomer REST API
    @DisplayName("Junit test for deleteCustomer REST API")
    @Test
    public void givenCustomerId_whenDeleteCustomer_thenReturn200() throws Exception
    {
        //Given - precondition
        Long customerId = 1L;

        /* Stubbing */
        willDoNothing().given(customerService).deleteCustomer(customerId);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/customer-service/apis/customers/{id}", customerId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
