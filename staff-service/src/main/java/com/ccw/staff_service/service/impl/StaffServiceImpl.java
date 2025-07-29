package com.ccw.staff_service.service.impl;

import com.ccw.staff_service.dto.StaffDto;
import com.ccw.staff_service.entity.Staff;
import com.ccw.staff_service.exception.ResourceNotFoundException;
import com.ccw.staff_service.repository.StaffRepository;
import com.ccw.staff_service.service.StaffService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StaffServiceImpl implements StaffService {

    private StaffRepository staffRepository;

    private ModelMapper modelMapper;

    @Override
    public StaffDto createStaff(StaffDto staffDto) {

        Staff staff = modelMapper.map(staffDto, Staff.class);

        Staff savedStaff = staffRepository.save(staff);

        return modelMapper.map(savedStaff, StaffDto.class);
    }

    @Override
    public List<StaffDto> getAllStaff() {
        return staffRepository.findAll().stream().map((staff)->modelMapper.map(staff, StaffDto.class)).collect(Collectors.toList());
    }

    @Override
    public StaffDto getStaffById(Long staffId) {

        //Staff foundStaff = staffRepository.findById(staffId).orElseThrow(()->new ResourceNotFoundException("Staff with ID : " + staffId + " does not exist !"));
        Staff foundStaff = staffRepository.findById(staffId).orElseThrow(()->new ResourceNotFoundException("Staff", "staffId", staffId));

        return modelMapper.map(foundStaff, StaffDto.class);
    }

    @Override
    public StaffDto updateStaff(StaffDto staffDto, Long staffId) {

        //Staff foundStaff = staffRepository.findById(staffId).orElseThrow(()->new ResourceNotFoundException("Staff with ID : " + staffId + " does not exist !"));
        Staff foundStaff = staffRepository.findById(staffId).orElseThrow(()->new ResourceNotFoundException("Staff", "staffId", staffId));

        foundStaff.setStaffFirstName(staffDto.getStaffFirstName());
        foundStaff.setStaffLastName(staffDto.getStaffLastName());
        foundStaff.setStaffEmail(staffDto.getStaffEmail());
        foundStaff.setStaffPassword(staffDto.getStaffPassword());

        Staff updatedStaff = staffRepository.save(foundStaff);

        return modelMapper.map(updatedStaff, StaffDto.class);
    }

    @Override
    public void deleteStaff(Long staffId) {

        //Staff foundStaff = staffRepository.findById(staffId).orElseThrow(()->new ResourceNotFoundException("Staff with ID : " + staffId + " does not exist !"));
        Staff foundStaff = staffRepository.findById(staffId).orElseThrow(()->new ResourceNotFoundException("Staff", "staffId", staffId));

        staffRepository.deleteById(staffId);
    }
}
