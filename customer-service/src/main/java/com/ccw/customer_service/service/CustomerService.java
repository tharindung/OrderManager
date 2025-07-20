package com.ccw.customer_service.service;

import com.ccw.customer_service.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    public CustomerDto createCustomer(CustomerDto customerDto);

    public List<CustomerDto> getAllCustomers();

    public CustomerDto getCustomerById(Long customerId);

    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerId);

    public void deleteCustomer(Long customerId);
}
