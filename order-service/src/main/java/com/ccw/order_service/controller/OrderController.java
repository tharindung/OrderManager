package com.ccw.order_service.controller;


import com.ccw.order_service.dto.CustomOrderResponseDto;
import com.ccw.order_service.dto.OrderDto;
import com.ccw.order_service.entity.Order;
import com.ccw.order_service.service.OrderService;
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
@RequestMapping("/order-service/apis/orders")
@Tag(name="Orders", description="CRUD operations for Orders")
public class OrderController {

    private OrderService orderService;

    @Operation(
            summary = "Create a new order",
            description = "Creates and returns a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "order created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto)
    {
        OrderDto savedOrder = orderService.createOrder(orderDto);

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all orders",
            description = "Returns a list of all orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of orders",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = OrderDto.class)))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders()
    {
        List<OrderDto> allOrders = orderService.getAllOrders();

        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @Operation(
            summary = "Get an order by ID",
            description = "Returns an order by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "order found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomOrderResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    //public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long orderId)
    public ResponseEntity<CustomOrderResponseDto> getOrderById(@PathVariable("id") Long orderId)
    {
        //OrderDto foundOrder = orderService.getOrderById(orderId);
        CustomOrderResponseDto customOrderResponseDto = orderService.getOrderById(orderId);

        //return new ResponseEntity<>(foundOrder, HttpStatus.OK);
        return new ResponseEntity<>(customOrderResponseDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Update an order",
            description = "Updates and returns an order by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "order updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody @Valid OrderDto orderDto, @PathVariable("id") Long orderId)
    {
        OrderDto updatedOrder = orderService.updateOrder(orderDto, orderId);

        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete an order",
            description = "Deletes an order by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order with passed ID deleted successfully", content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long orderId)
    {
        orderService.deleteOrder(orderId);

        return new ResponseEntity<>("Order with ID : " + orderId + " deleted successfully !", HttpStatus.OK);
    }
}
