package com.ccw.customer_service.repository;

import com.ccw.customer_service.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    public void setup(){

        customer = Customer.builder()
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john.doe@gmail.com")
                .customerPassword("12345").build();

    }

    //Junit test for create customer operation
    @Test
    @DisplayName("Junit test for create customer operation")
    public void givenCustomerObject_whenSave_thenReturnSavedCustomer(){

        //Given - precondition
        /*Customer customer = Customer.builder()
                            .customerFirstName("John")
                            .customerLastName("Doe")
                            .customerEmail("john.doe@gmail.com")
                            .customerPassword("12345").build();*/

        //When - behavior we are testing
        Customer savedCustomer = customerRepository.save(customer);

        //Then - verifying the output
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustomerId()).isGreaterThan(0);
    }

    //Junit test for get all customers operation
    @DisplayName("Junit test for get all customers operation")
    @Test
    public void givenCustomerList_whenFindAll_thenReturnCustomersList()
    {
        //Given - precondition
        /*Customer customer1 = Customer.builder()
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john.doe@gmail.com")
                .customerPassword("12345").build();*/

        Customer customer2 = Customer.builder()
                .customerFirstName("Jane")
                .customerLastName("Doe")
                .customerEmail("jane.doe@gmail.com")
                .customerPassword("12345").build();

        customerRepository.save(customer);
        customerRepository.save(customer2);

        //When - behavior we are testing
        List<Customer> customerList = customerRepository.findAll();

        //Then - verifying the output
        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isEqualTo(2);

    }

    //Junit test for Get Customer By ID operation
    @DisplayName("Junit test for Get Customer By ID operation")
    @Test
    public void givenCustomerObject_whenFindById_thenReturnCustomerObject()
    {
        //Given - precondition
        /*Customer customer = Customer.builder()
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john.doe@gmail.com")
                .customerPassword("12345").build();*/

        customerRepository.save(customer);

        //When - behavior we are testing
        Customer foundCustomer = customerRepository.findById(customer.getCustomerId()).get();

        //Then - verifying the output
        assertThat(foundCustomer).isNotNull();
    }

    //Junit test for update customer operation
    @DisplayName("Junit test for update customer operation")
    @Test
    public void givenCustomerObject_whenUpdateCustomer_thenReturnUpdatedCustomer()
    {
        //Given - precondition
        /*Customer customer = Customer.builder()
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john.doe@gmail.com")
                .customerPassword("12345").build();*/

        customerRepository.save(customer);

        //When - behavior we are testing
        Customer foundCustomer = customerRepository.findById(customer.getCustomerId()).get();

        foundCustomer.setCustomerFirstName("Mark");
        foundCustomer.setCustomerLastName("Smith");

        Customer updatedCustomer = customerRepository.save(foundCustomer);

        //Then - verifying the output
        assertThat(updatedCustomer.getCustomerFirstName()).isEqualTo("Mark");
        assertThat(updatedCustomer.getCustomerLastName()).isEqualTo("Smith");
    }

    //Junit test for delete customer operation
    @DisplayName("Junit test for delete customer operation")
    @Test
    public void givenCustomerObject_whenDelete_thenRemoveCustomer()
    {
        //Given - precondition
        /*Customer customer = Customer.builder()
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john.doe@gmail.com")
                .customerPassword("12345").build();*/

        customerRepository.save(customer);

        //When - behavior we are testing
        customerRepository.deleteById(customer.getCustomerId());

        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getCustomerId());

        //Then - verifying the output
        assertThat(optionalCustomer).isEmpty();
    }
}
