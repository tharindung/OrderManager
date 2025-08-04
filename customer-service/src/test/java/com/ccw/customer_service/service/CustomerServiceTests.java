package com.ccw.customer_service.service;


import com.ccw.customer_service.dto.CustomerDto;
import com.ccw.customer_service.entity.Customer;
import com.ccw.customer_service.exception.ResourceNotFoundException;
import com.ccw.customer_service.repository.CustomerRepository;
import com.ccw.customer_service.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private ModelMapper modelMapper;

    private CustomerDto customerDto;

    private Customer customer;

    @BeforeEach
    public void setup()
    {
        customerDto = CustomerDto.builder()
                .customerId(1L)
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john.doe@gmail.com")
                .customerPassword("12345").build();

        customer = Customer.builder()
                .customerId(1L)
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john.doe@gmail.com")
                .customerPassword("12345").build();
    }


    //Junit test for createCustomer method
    @DisplayName("Junit test for createCustomer method")
    @Test
    public void givenCustomerDtoObject_whenCreateCustomer_thenReturnSavedCustomerDtoObject()
    {
        //Given - precondition
        given(customerRepository.save(customer)).willReturn(customer);

        doReturn(customer).when(modelMapper).map(customerDto, Customer.class);

        doReturn(customerDto).when(modelMapper).map(customer, CustomerDto.class);

        //When - behavior we are testing
        CustomerDto savedCustomer = customerService.createCustomer(customerDto);

        //Then - verifying the output
        assertThat(savedCustomer).isNotNull();
    }

    //Junit test for getAllCustomers method - positive scenario
    @DisplayName("Junit test for getAllCustomers method - positive scenario")
    @Test
    public void givenCustomersList_whenGetAllCustomers_thenReturnCustomerDtoList()
    {
        //Given - precondition
        Customer customer1 = Customer.builder()
                .customerFirstName("Mark")
                .customerLastName("Smith")
                .customerEmail("mark.smith@gmail.com")
                .customerPassword("12345").build();

        given(customerRepository.findAll()).willReturn(List.of(customer, customer1));

        //When - behavior we are testing
        List<CustomerDto> allCustomers = customerService.getAllCustomers();

        //Then - verifying the output
        assertThat(allCustomers).isNotNull();
        assertThat(allCustomers.size()).isEqualTo(2);
    }

    //Junit test for getAllCustomers method - negative scenario
    @DisplayName("Junit test for getAllCustomers method - negative scenario")
    @Test
    public void givenEmptyCustomerList_whenGetAllCustomers_thenReturnEmptyCustomerDtoList()
    {
        //Given - precondition

        given(customerRepository.findAll()).willReturn(Collections.emptyList());

        //When - behavior we are testing
        List<CustomerDto> allCustomers = customerService.getAllCustomers();

        //Then - verifying the output
        assertThat(allCustomers).isEmpty();
        assertThat(allCustomers.size()).isEqualTo(0);
    }

    //Junit test for getCustomerById method - positive scenario
    @DisplayName("Junit test for getCustomerById method - positive scenario")
    @Test
    public void givenCustomerId_whenGetCustomerById_thenReturnCustomerDtoObject()
    {
        //Given - precondition
        given(customerRepository.findById(customer.getCustomerId())).willReturn(Optional.of(customer));

        doReturn(customerDto).when(modelMapper).map(customer, CustomerDto.class);

        //When - behavior we are testing
        CustomerDto returnedCustomer = customerService.getCustomerById(customer.getCustomerId());

        //Then - verifying the output
        assertThat(returnedCustomer).isNotNull();
    }

    //Junit test for getCustomerById method - negative scenario
    @DisplayName("Junit test for getCustomerById method - negative scenario")
    @Test
    public void givenInvalidCustomerId_whenGetCustomerById_thenReturnResourceNotFoundException()
    {
        //Given - precondition
        Long invalidCustomerId = 99L;

        given(customerRepository.findById(invalidCustomerId)).willThrow(new ResourceNotFoundException("Customer", "customerId", invalidCustomerId));

        //When - behavior we are testing & Then - verifying the output
        assertThatThrownBy( ()->{
            customerService.getCustomerById(invalidCustomerId);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer not found with customerId : 99");

    }

    //Junit test for updateCustomer method
    @DisplayName("Junit test for updateCustomer method")
    @Test
    public void givenCustomerDtoObject_whenUpdateCustomer_thenReturnUpdatedCustomerDtoObject()
    {
        //Given - precondition
        given(customerRepository.findById(customer.getCustomerId())).willReturn(Optional.of(customer));

        given(customerRepository.save(customer)).willReturn(customer);

        doReturn(customerDto).when(modelMapper).map(customer, CustomerDto.class);

        customer.setCustomerFirstName("Mark");
        customer.setCustomerLastName("Smith");

        customerDto.setCustomerFirstName("Mark");
        customerDto.setCustomerLastName("Smith");

        //When - behavior we are testing
        CustomerDto updatedCustomerDto = customerService.updateCustomer(customerDto, customerDto.getCustomerId());

        //Then - verifying the output
        assertThat(updatedCustomerDto.getCustomerFirstName()).isEqualTo("Mark");
        assertThat(updatedCustomerDto.getCustomerLastName()).isEqualTo("Smith");
    }

    //Junit test for deleteCustomer method
    @DisplayName("Junit test for deleteCustomer method")
    @Test
    public void givenCustomerId_whenDeleteCustomer_thenReturnNothing()
    {
        //Given - precondition
        given(customerRepository.findById(customer.getCustomerId())).willReturn(Optional.of(customer));

        willDoNothing().given(customerRepository).deleteById(customer.getCustomerId());

        //When - behavior we are testing
        customerService.deleteCustomer(customer.getCustomerId());

        //Then - verifying the output
        verify(customerRepository, times(1)).deleteById(customer.getCustomerId());
    }
}
