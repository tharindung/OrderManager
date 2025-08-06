package com.ccw.order_service.repository;

import com.ccw.order_service.entity.Order;
import com.ccw.order_service.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StatusRepository statusRepository;

    private Order order;

    private Status status;

    @BeforeEach
    public void setup()
    {
        status = Status.builder().status("Assigned").build();

        statusRepository.save(status);

        order = Order.builder()
                .orderDate(LocalDate.parse("2025-07-05"))
                .orderDesc("Website for a restaurant")
                .orderPrice(1250.00)
                .orderStatus(status).build();
    }

    //Junit test for create order operation
    @DisplayName("Junit test for create order operation")
    @Test
    public void givenOrderObject_whenSave_thenReturnSavedOrder()
    {
        //Given - precondition

        //When - behavior we are testing
        Order savedOrder = orderRepository.save(order);

        //Then - verifying the output
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getOrderId()).isGreaterThan(0);
    }

    //Junit test for get all orders operation
    @DisplayName("Junit test for get all orders operation")
    @Test
    public void givenOrdersList_whenFindAll_thenReturnOrdersList()
    {
        //Given - precondition
        Order order1 = Order.builder()
                .orderDate(LocalDate.parse("2025-07-10"))
                .orderDesc("Design a Banner for Marketing Campaign")
                .orderPrice(750.00)
                .orderStatus(status).build();

        orderRepository.save(order);
        orderRepository.save(order1);

        //When - behavior we are testing
        List<Order> ordersList = orderRepository.findAll();

        //Then - verifying the output
        assertThat(ordersList).isNotNull();
        assertThat(ordersList.size()).isEqualTo(2);
    }

    //Junit test for get order by id
    @DisplayName("Junit test for get order by id")
    @Test
    public void givenOrderObject_whenFindById_thenReturnOrderObject()
    {
        //Given - precondition
        orderRepository.save(order);

        //When - behavior we are testing
        Order foundOrder = orderRepository.findById(order.getOrderId()).get();

        //Then - verifying the output
        assertThat(foundOrder).isNotNull();
    }

    //Junit test for update order operation
    @DisplayName("Junit test for update order operation")
    @Test
    public void givenOrderObject_whenUpdateOrder_thenReturnUpdatedOrder()
    {
        //Given - precondition
        orderRepository.save(order);

        //When - behavior we are testing
        Order foundOrder = orderRepository.findById(order.getOrderId()).get();

        foundOrder.setOrderDate(LocalDate.parse("2025-07-20"));
        foundOrder.setOrderPrice(2500.00);

        Order updatedOrder = orderRepository.save(foundOrder);

        //Then - verifying the output
        assertThat(updatedOrder.getOrderDate()).isEqualTo(LocalDate.parse("2025-07-20"));
        assertThat(updatedOrder.getOrderPrice()).isEqualTo(2500.00);
    }

    //Junit test for delete order operation
    @DisplayName("Junit test for delete order operation")
    @Test
    public void givenOrderObject_whenDelete_thenRemoveOrder()
    {
        //Given - precondition
        orderRepository.save(order);

        //When - behavior we are testing
        orderRepository.deleteById(order.getOrderId());

        Optional<Order> orderOptional = orderRepository.findById(order.getOrderId());

        //Then - verifying the output
        assertThat(orderOptional).isEmpty();
    }
}
