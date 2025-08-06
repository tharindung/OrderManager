package com.ccw.staff_service.controller;

import com.ccw.staff_service.dto.StaffDto;
import com.ccw.staff_service.entity.Staff;
import com.ccw.staff_service.exception.ResourceNotFoundException;
import com.ccw.staff_service.service.StaffService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@WebMvcTest
public class StaffControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StaffService staffService;

    @Autowired
    private ObjectMapper objectMapper;

    private StaffDto staffDto;

    @BeforeEach
    public void setup()
    {
        staffDto = StaffDto.builder()
                .staffFirstName("John")
                .staffLastName("Doe")
                .staffEmail("john.doe@gmail.com")
                .staffPassword("12345").build();
    }

    //Junit test for createStaff REST API
    @DisplayName("Junit test for createStaff REST API")
    @Test
    public void givenStaffDtoObject_whenCreateStaff_thenReturnSavedStaffDtoObject() throws Exception
    {
        //Given - precondition

        /* Stubbing */
        given(staffService.createStaff(ArgumentMatchers.any(StaffDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/staff-service/apis/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(staffDto)));

        //Then - verifying the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.staffFirstName", CoreMatchers.is(staffDto.getStaffFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.staffLastName", CoreMatchers.is(staffDto.getStaffLastName())));
    }

    //Junit test for getAllStaff REST API - positive scenario
    @DisplayName("Junit test for getAllStaff REST API - positive scenario")
    @Test
    public void givenStaffDtoList_whenGetAllStaff_thenReturnStaffDtoList() throws Exception
    {
        //Given - precondition
        StaffDto staffDto1 = StaffDto.builder()
                .staffFirstName("Jane")
                .staffLastName("Doe")
                .staffEmail("jane.doe@gmail.com")
                .staffPassword("12345").build();

        List<StaffDto> listOfStaff = new ArrayList<>();
        listOfStaff.add(staffDto);
        listOfStaff.add(staffDto1);

        /* Stubbing */
        given(staffService.getAllStaff()).willReturn(listOfStaff);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/staff-service/apis/staff"));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfStaff.size())));
    }

    //Junit test for getAllStaff REST API - negative scenario
    @DisplayName("unit test for getAllStaff REST API - negative scenario")
    @Test
    public void givenEmptyStaffDtoList_whenGetAllStaff_thenReturnEmptyStaffDtoList() throws Exception
    {
        //Given - precondition
        List<StaffDto> listOfStaff = new ArrayList<>();

        /* Stubbing */
        given(staffService.getAllStaff()).willReturn(Collections.emptyList());

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/staff-service/apis/staff"));

        //Then - verifying the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfStaff.size())));
    }

    //Junit test for getStaffById REST API - positive scenario
    @DisplayName("Junit test for getStaffById REST API - positive scenario")
    @Test
    public void givenStaffId_whenGetStaffById_thenReturnStaffDtoObject() throws Exception
    {
        //Given - precondition
        Long staffId = 1L;

        /* Stubbing */
        given(staffService.getStaffById(staffId)).willReturn(staffDto);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/staff-service/apis/staff/{id}", staffId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.staffFirstName",CoreMatchers.is(staffDto.getStaffFirstName())));
    }

    //Junit test for getStaffById REST API - negative scenario
    @DisplayName("Junit test for getStaffById REST API - negative scenario")
    @Test
    public void givenInvalidStaffId_whenGetStaffById_thenReturnResourceNotFoundException() throws Exception
    {
        //Given - precondition
        Long invalidStaffId = 99L;

        /* Stubbing */
        given(staffService.getStaffById(invalidStaffId)).willThrow(new ResourceNotFoundException("Staff", "staffId", invalidStaffId));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/staff-service/apis/staff/{id}", invalidStaffId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    //Junit test for updateStaff REST API
    @DisplayName("Junit test for updateStaff REST API")
    @Test
    public void givenStaffDtoObject_whenUpdateStaff_thenReturnUpdatedStaffDtoObject() throws Exception
    {
        //Given - precondition
        Long staffId = 1L;

        StaffDto updatedStaffDto = StaffDto.builder()
                                    .staffFirstName("Mark")
                                    .staffLastName("David")
                                    .staffEmail("mark.david@gmail.com")
                                    .staffPassword("56789").build();

        /* Stubbing */
        given(staffService.getStaffById(staffId)).willReturn(staffDto);

        /* Stubbing */
        given(staffService.updateStaff(ArgumentMatchers.any(StaffDto.class), ArgumentMatchers.any(Long.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/staff-service/apis/staff/{id}", staffId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStaffDto)));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.staffFirstName", CoreMatchers.is(updatedStaffDto.getStaffFirstName())));
    }

    //Junit test for deleteStaff REST API
    @DisplayName("Junit test for deleteStaff REST API")
    @Test
    public void givenStaffId_whenDeleteStaff_thenReturn200() throws Exception
    {
        //Given - precondition
        Long staffId = 1L;

        /* Stubbing */
        willDoNothing().given(staffService).deleteStaff(staffId);

        //When - behavior we are testing
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/staff-service/apis/staff/{id}", staffId));

        //Then - verifying the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
