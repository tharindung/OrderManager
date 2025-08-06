package com.ccw.staff_service.service;

import com.ccw.staff_service.dto.StaffDto;
import com.ccw.staff_service.entity.Staff;
import com.ccw.staff_service.exception.ResourceNotFoundException;
import com.ccw.staff_service.repository.StaffRepository;
import com.ccw.staff_service.service.impl.StaffServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTests {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffServiceImpl staffService;

    @Mock
    private ModelMapper modelMapper;

    private Staff staff;

    private StaffDto staffDto;

    @BeforeEach
    public void setup()
    {
        staff = Staff.builder()
                .staffId(1L)
                .staffFirstName("Jane")
                .staffLastName("Doe")
                .staffEmail("jane.doe@gmail.com")
                .staffPassword("12345").build();

        staffDto = StaffDto.builder()
                .staffId(1L)
                .staffFirstName("Jane")
                .staffLastName("Doe")
                .staffEmail("jane.doe@gmail.com")
                .staffPassword("12345").build();
    }

    //Junit test for createStaff method
    @DisplayName("Junit test for createStaff method")
    @Test
    public void givenStaffDtoObject_whenCreateStaff_thenReturnSavedStaffDtoObject()
    {
        //Given - precondition
        given(staffRepository.save(staff)).willReturn(staff);

        doReturn(staffDto).when(modelMapper).map(staff, StaffDto.class);

        doReturn(staff).when(modelMapper).map(staffDto, Staff.class);

        //When - behavior we are testing
        StaffDto savedStaff = staffService.createStaff(staffDto);

        //Then - verifying the output
        assertThat(savedStaff).isNotNull();
    }

    //Junit test for getAllStaff method - positive scenario
    @DisplayName("Junit test for getAllStaff method - positive scenario")
    @Test
    public void givenStaffList_whenGetAllStaff_thenReturnStaffDtoList()
    {
        //Given - precondition
        Staff staff1 = Staff.builder()
                .staffId(2L)
                .staffFirstName("Mark")
                .staffLastName("Smith")
                .staffEmail("mark.smith@gmail.com")
                .staffPassword("12345").build();

        given(staffRepository.findAll()).willReturn(List.of(staff, staff1));

        //When - behavior we are testing
        List<StaffDto> allStaff = staffService.getAllStaff();

        //Then - verifying the output
        assertThat(allStaff).isNotNull();
        assertThat(allStaff.size()).isEqualTo(2);
    }

    //Junit test for getAllStaff method - negative scenario
    @DisplayName("Junit test for getAllStaff method - negative scenario")
    @Test
    public void givenEmptyStaffList_whenGetAllStaff_thenReturnEmptyStaffDtoList()
    {
        //Given - precondition
        given(staffRepository.findAll()).willReturn(Collections.emptyList());

        //When - behavior we are testing
        List<StaffDto> allStaff = staffService.getAllStaff();

        //Then - verifying the output
        assertThat(allStaff).isEmpty();
        assertThat(allStaff.size()).isEqualTo(0);
    }

    //Junit test for getStaffById method - positive scenario
    @DisplayName("Junit test for getStaffById method - positive scenario")
    @Test
    public void givenStaffId_whenGetStaffId_thenReturnStaffDtoObject()
    {
        //Given - precondition
        given(staffRepository.findById(staff.getStaffId())).willReturn(Optional.of(staff));

        doReturn(staffDto).when(modelMapper).map(staff, StaffDto.class);

        //When - behavior we are testing
        StaffDto returnedStaff = staffService.getStaffById(staff.getStaffId());

        //Then - verifying the output
        assertThat(returnedStaff).isNotNull();
    }

    //Junit test for getStaffById method - negative scenario
    @DisplayName("Junit test for getStaffById method - negative scenario")
    @Test
    public void givenInvalidStaffId_whenGetStaffById_thenReturnResourceNotFoundException()
    {
        //Given - precondition
        Long invalidStaffId = 99L;

        given(staffRepository.findById(invalidStaffId)).willThrow(new ResourceNotFoundException("Staff", "staffId", invalidStaffId));

        //When - behavior we are testing & Then - verifying the output
        assertThatThrownBy(()->{
            staffService.getStaffById(invalidStaffId);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Staff not found with staffId : 99");
    }

    //Junit test for updateStaff method
    @DisplayName("Junit test for updateStaff method")
    @Test
    public void givenStaffDtoObject_whenUpdateStaff_thenReturnUpdatedStaffDtoObject()
    {
        //Given - precondition
        given(staffRepository.findById(staff.getStaffId())).willReturn(Optional.of(staff));

        given(staffRepository.save(staff)).willReturn(staff);

        doReturn(staffDto).when(modelMapper).map(staff, StaffDto.class);

        staff.setStaffFirstName("David");
        staff.setStaffLastName("Jones");

        staffDto.setStaffFirstName("David");
        staffDto.setStaffLastName("Jones");

        //When - behavior we are testing
        StaffDto updatedStaff = staffService.updateStaff(staffDto, staffDto.getStaffId());

        //Then - verifying the output
        assertThat(updatedStaff.getStaffFirstName()).isEqualTo("David");
        assertThat(updatedStaff.getStaffLastName()).isEqualTo("Jones");
    }

    //Junit test for deleteStaff method
    @DisplayName("Junit test for deleteStaff method")
    @Test
    public void givenStaffId_whenDeleteStaff_thenReturnNothing()
    {
        //Given - precondition
        given(staffRepository.findById(staff.getStaffId())).willReturn(Optional.of(staff));

        willDoNothing().given(staffRepository).deleteById(staff.getStaffId());

        //When - behavior we are testing
        staffService.deleteStaff(staff.getStaffId());

        //Then - verifying the output
        verify(staffRepository, times(1)).deleteById(staff.getStaffId());
    }
}
