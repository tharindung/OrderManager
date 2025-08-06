package com.ccw.order_service.controller;

import com.ccw.order_service.dto.StatusDto;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@WebMvcTest
public class StatusControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StatusService statusService;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private StatusDto statusDto;

    @BeforeEach
    public void setup()
    {
        statusDto = StatusDto.builder()
                .status("Assigned").build();
    }

    //Junit test for createStatus REST API
    @DisplayName("Junit test for createStatus REST API")
    @Test
    public void givenStatusDtoObject_whenCreateStatus_thenReturnSavedStatusDtoObject() throws Exception
    {
        //Given - precondition

        /* Stubbing */
        given(statusService.createStatus(ArgumentMatchers.any(StatusDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/order-service/apis/statuses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusDto)));

        //Then - verifying the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(statusDto.getStatus())));
    }

    //Junit test for getAllStatuses REST API - positive scenario
    @DisplayName("Junit test for getAllStatuses REST API - positive scenario")
    @Test
    public void givenStatusDtoList_whenGetAllStatuses_thenReturnStatusDtoList() throws Exception
    {
        //Given - precondition
        List<StatusDto> statusDtoList = new ArrayList<>();
        statusDtoList.add(statusDto);
        statusDtoList.add(StatusDto.builder().status("Completed").build());

        /* Stubbing */
        given(statusService.getAllStatuses()).willReturn(statusDtoList);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/apis/statuses"));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(statusDtoList.size())));
    }

    //Junit test for getAllStatuses REST API - negative scenario
    @DisplayName("Junit test for getAllStatuses REST API - negative scenario")
    @Test
    public void givenEmptyStatusDtoList_whenGetAllStatuses_thenReturnEmptyStatusDtoList() throws Exception
    {
        //Given - precondition
        List<StatusDto> listOfStatuses = new ArrayList<>();

        /* Stubbing */
        given(statusService.getAllStatuses()).willReturn(Collections.emptyList());

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/apis/statuses"));

        //Then - verifying the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfStatuses.size())));
    }

    //Junit test for getStatusById REST API - positive scenario
    @DisplayName("Junit test for getStatusById REST API - positive scenario")
    @Test
    public void givenStatusId_whenGetStatusById_thenReturnStatusDtoObject() throws Exception
    {
        //Given - precondition
        Integer statusId = 1;

        /* Stubbing */
        given(statusService.getStatusById(statusId)).willReturn(statusDto);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/apis/statuses/{id}", statusId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(statusDto.getStatus())));
    }

    //Junit test for getStatusById REST API - negative scenario
    @DisplayName("Junit test for getStatusById REST API - negative scenario")
    @Test
    public void givenInvalidStatusId_whenGetStatusById_thenReturnResourceNotFoundException() throws Exception
    {
        //Given - precondition
        Integer invalidStatusId = 99;

        /* Stubbing */
        given(statusService.getStatusById(invalidStatusId)).willThrow(new ResourceNotFoundException("Status", "statusId", invalidStatusId.longValue()));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/apis/statuses/{id}", invalidStatusId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    //Junit test for updateStatus REST API
    @DisplayName("Junit test for updateStatus REST API")
    @Test
    public void givenStatusDtoObject_whenUpdateStatus_thenReturnUpdatedStatusDtoObject() throws Exception
    {
        //Given - precondition
        Integer statusId = 1;

        StatusDto updatedStatusDto = StatusDto.builder()
                                    .status("In-progress").build();

        /* Stubbing */
        given(statusService.getStatusById(statusId)).willReturn(statusDto);

        /* Stubbing */
        given(statusService.updateStatus(ArgumentMatchers.any(StatusDto.class), ArgumentMatchers.any(Integer.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/order-service/apis/statuses/{id}", statusId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStatusDto)));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(updatedStatusDto.getStatus())));
    }

    //Junit test for deleteStatus REST API
    @DisplayName("Junit test for deleteStatus REST API")
    @Test
    public void givenStatusId_whenDeleteStatus_thenReturn200() throws Exception
    {
        //Given - precondition
        Integer statusId = 1;

        /* Stubbing */
        willDoNothing().given(statusService).deleteStatus(statusId);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/order-service/apis/statuses/{id}", statusId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
