package com.ccw.staff_service.service;

import com.ccw.staff_service.dto.StaffDto;

import java.util.List;

public interface StaffService {

    public StaffDto createStaff(StaffDto staffDto);

    public List<StaffDto> getAllStaff();

    public StaffDto getStaffById(Long staffId);

    public StaffDto updateStaff(StaffDto staffDto, Long staffId);

    public void deleteStaff(Long staffId);
}
