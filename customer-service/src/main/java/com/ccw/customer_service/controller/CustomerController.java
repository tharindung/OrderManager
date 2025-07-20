package com.ccw.customer_service.controller;

import com.ccw.customer_service.dto.CustomerDto;
import com.ccw.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customer-service/apis/customers")
public class CustomerController {

    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Valid CustomerDto customerDto)
    {
        CustomerDto savedCustomer = customerService.createCustomer(customerDto);

        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers()
    {
        List<CustomerDto> allCustomers = customerService.getAllCustomers();

        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long customerId)
    {
        CustomerDto foundCustomer = customerService.getCustomerById(customerId);

        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody @Valid CustomerDto customerDto, @PathVariable("id") Long customerId)
    {
        CustomerDto updatedCustomer = customerService.updateCustomer(customerDto, customerId);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long customerId)
    {
        customerService.deleteCustomer(customerId);

        return new ResponseEntity<>("Customer with ID : " + customerId + " deleted successfully !", HttpStatus.OK);
    }
}
