package com.ccw.customer_service.controller;

import com.ccw.customer_service.dto.CustomerDto;
import com.ccw.customer_service.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customer-service/apis/customers")
@Tag(name="Customers", description="CRUD operations for Customers")
public class CustomerController {

    private CustomerService customerService;

    @Operation(
            summary = "Create a new customer",
            description = "Creates and returns a new customer",
            responses = {
                    @ApiResponse(responseCode = "201", description = "customer created",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Valid CustomerDto customerDto)
    {
        CustomerDto savedCustomer = customerService.createCustomer(customerDto);

        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all customers",
            description = "Returns a list of all customers",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of customers",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CustomerDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers()
    {
        List<CustomerDto> allCustomers = customerService.getAllCustomers();

        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a customer by ID",
            description = "Returns a customer by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "customer found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerDto.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long customerId)
    {
        CustomerDto foundCustomer = customerService.getCustomerById(customerId);

        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a customer",
            description = "Updates and returns a customer by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "customer updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody @Valid CustomerDto customerDto, @PathVariable("id") Long customerId)
    {
        CustomerDto updatedCustomer = customerService.updateCustomer(customerDto, customerId);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a customer",
            description = "Deletes a customer by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer with passed ID deleted successfully", content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long customerId)
    {
        customerService.deleteCustomer(customerId);

        return new ResponseEntity<>("Customer with ID : " + customerId + " deleted successfully !", HttpStatus.OK);
    }
}
