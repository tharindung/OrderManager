package com.ccw.order_service.service;

import com.ccw.order_service.dto.StatusDto;
import com.ccw.order_service.entity.Status;
import com.ccw.order_service.exception.ResourceNotFoundException;
import com.ccw.order_service.repository.StatusRepository;
import com.ccw.order_service.service.impl.StatusServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTests {

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private StatusServiceImpl statusService;

    @Mock
    private ModelMapper modelMapper;

    private Status status;

    private StatusDto statusDto;

    @BeforeEach
    public void setup()
    {
        status = Status.builder()
                .statusId(1)
                .status("Assigned").build();

        statusDto = StatusDto.builder()
                .statusId(1)
                .status("Assigned").build();
    }

    //Junit test for createStatus method
    @DisplayName("Junit test for createStatus method")
    @Test
    public void givenStatusDtoObject_whenCreateStatus_thenReturnSavedStatusDtoObject()
    {
        //Given - precondition
        given(statusRepository.save(status)).willReturn(status);

        doReturn(status).when(modelMapper).map(statusDto, Status.class);

        doReturn(statusDto).when(modelMapper).map(status, StatusDto.class);

        //When - behavior we are testing
        StatusDto savedStatusDto = statusService.createStatus(statusDto);

        //Then - verifying the output
        assertThat(savedStatusDto).isNotNull();
    }

    //Junit test for getAllStatuses method - positive scenario
    @DisplayName("Junit test for getAllStatuses - positive scenario")
    @Test
    public void givenStatusList_whenGetAllStatuses_thenReturnStatusDtoList()
    {
        //Given - precondition
        Status status1 = Status.builder()
                .statusId(2)
                .status("Completed").build();

        given(statusRepository.findAll()).willReturn(List.of(status, status1));

        //When - behavior we are testing
        List<StatusDto> allStatuses = statusService.getAllStatuses();

        //Then - verifying the output
        assertThat(allStatuses).isNotNull();
        assertThat(allStatuses.size()).isEqualTo(2);
    }

    //Junit test for getAllStatuses method - negative scenario
    @DisplayName("Junit test for getAllStatuses - negative scenario")
    @Test
    public void givenEmptyStatusList_whenGetAllStatuses_thenReturnEmptyStatusDtoList()
    {
        //Given - precondition
        given(statusRepository.findAll()).willReturn(Collections.emptyList());

        //When - behavior we are testing
        List<StatusDto> allStatuses = statusService.getAllStatuses();

        //Then - verifying the output
        assertThat(allStatuses).isEmpty();
        assertThat(allStatuses.size()).isEqualTo(0);
    }

    //Junit test for getStatuseById method - positive scenario
    @DisplayName("Junit test for getStatuseById method - positive scenario")
    @Test
    public void givenStatusId_whenGetStatusById_thenReturnStatusDtoObject()
    {
        //Given - precondition
        given(statusRepository.findById(status.getStatusId())).willReturn(Optional.of(status));

        doReturn(statusDto).when(modelMapper).map(status, StatusDto.class);

        //When - behavior we are testing
        StatusDto returnedStatus = statusService.getStatusById(status.getStatusId());

        //Then - verifying the output
        assertThat(returnedStatus).isNotNull();
    }

    //Junit test for getStatuseById method - negative scenario
    @DisplayName("Junit test for getStatuseById method - negative scenario")
    @Test
    public void givenInvalidStatusId_whenGetStatusById_thenReturnResourceNotFoundException()
    {
        //Given - precondition
        Integer invalidStatusId = 99;

        given(statusRepository.findById(invalidStatusId)).willThrow(new ResourceNotFoundException("Status", "statusId", invalidStatusId.longValue()));

        //When - behavior we are testing & Then - verifying the output
        assertThatThrownBy(()->{
            statusService.getStatusById(invalidStatusId);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Status not found with statusId : 99");
    }

    //Junit test for updateStatus method
    @DisplayName("Junit test for updateStatus method")
    @Test
    public void givenStatusDtoObject_whenUpdateStatus_thenReturnUpdatedStatusDtoObject()
    {
        //Given - precondition
        given(statusRepository.findById(status.getStatusId())).willReturn(Optional.of(status));

        given(statusRepository.save(status)).willReturn(status);

        doReturn(statusDto).when(modelMapper).map(status, StatusDto.class);

        status.setStatus("In-progress");
        statusDto.setStatus("In-progress");

        //When - behavior we are testing
        StatusDto updatedStatus = statusService.updateStatus(statusDto, statusDto.getStatusId());

        //Then - verifying the output
        assertThat(updatedStatus.getStatus()).isEqualTo("In-progress");
    }

    //Junit test for deleteStatus method
    @DisplayName("Junit test for deleteStatus method")
    @Test
    public void givenStatusId_whenDeleteStatus_thenReturnNothing()
    {
        //Given - precondition
        given(statusRepository.findById(status.getStatusId())).willReturn(Optional.of(status));

        willDoNothing().given(statusRepository).deleteById(status.getStatusId());

        //When - behavior we are testing
        statusService.deleteStatus(status.getStatusId());

        //Then - verifying the output
        verify(statusRepository, times(1)).deleteById(status.getStatusId());
    }
}
