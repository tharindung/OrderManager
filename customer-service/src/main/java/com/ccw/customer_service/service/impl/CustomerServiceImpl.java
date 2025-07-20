package com.ccw.customer_service.service.impl;

import com.ccw.customer_service.dto.CustomerDto;
import com.ccw.customer_service.entity.Customer;
import com.ccw.customer_service.exception.ResourceNotFoundException;
import com.ccw.customer_service.repository.CustomerRepository;
import com.ccw.customer_service.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private ModelMapper modelMapper;

    private CustomerRepository customerRepository;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {

        Customer customer = modelMapper.map(customerDto, Customer.class);

        Customer savedCustomer = customerRepository.save(customer);

        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {

        return customerRepository.findAll().stream().map((customer)->modelMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) {

        //Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer with ID : " + customerId + " does not exist !"));
        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer", "customerId", customerId));

        return modelMapper.map(foundCustomer, CustomerDto.class);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerId) {

        //Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer with ID : " + customerId + " does not exist !"));
        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer", "customerId", customerId));

        foundCustomer.setCustomerFirstName(customerDto.getCustomerFirstName());
        foundCustomer.setCustomerLastName(customerDto.getCustomerLastName());
        foundCustomer.setCustomerAddress(customerDto.getCustomerAddress());
        foundCustomer.setCustomerEmail(customerDto.getCustomerEmail());
        foundCustomer.setCustomerPassword(customerDto.getCustomerPassword());

        Customer updatedCustomer = customerRepository.save(foundCustomer);

        return modelMapper.map(updatedCustomer, CustomerDto.class);
    }

    @Override
    public void deleteCustomer(Long customerId) {

        //customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer with ID : "+customerId+ " does not exist !"));
        customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer", "customerId", customerId));

        customerRepository.deleteById(customerId);
    }
}
