package com.ccw.staff_service.controller;

import com.ccw.staff_service.dto.StaffDto;
import com.ccw.staff_service.service.StaffService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/staff-service/apis/staff")
public class StaffController {

    private StaffService staffService;

    @PostMapping
    public ResponseEntity<StaffDto> createStaff(@RequestBody @Valid StaffDto staffDto){

        StaffDto savedStaff = staffService.createStaff(staffDto);

        return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StaffDto>> getAllStaff()
    {
        List<StaffDto> allStaff = staffService.getAllStaff();

        return new ResponseEntity<>(allStaff, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable("id") Long staffId)
    {
        StaffDto foundStaff = staffService.getStaffById(staffId);

        return new ResponseEntity<>(foundStaff, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<StaffDto> updateStaff(@RequestBody @Valid StaffDto staffDto, @PathVariable("id") Long staffId)
    {
        StaffDto updatedStaff = staffService.updateStaff(staffDto, staffId);

        return new ResponseEntity<>(updatedStaff, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable("id") Long staffId)
    {
        staffService.deleteStaff(staffId);

        return new ResponseEntity<>("Staff with ID : " + staffId + " deleted successfully", HttpStatus.OK);
    }
}
