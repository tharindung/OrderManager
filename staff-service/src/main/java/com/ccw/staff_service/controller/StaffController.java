package com.ccw.staff_service.controller;

import com.ccw.staff_service.dto.StaffDto;
import com.ccw.staff_service.service.StaffService;
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
@RequestMapping("/staff-service/apis/staff")
@Tag(name="Staff", description="CRUD operations for Staff")
public class StaffController {

    private StaffService staffService;

    @Operation(
            summary = "Create a new staff member",
            description = "Creates and returns a new staff member",
            responses = {
                    @ApiResponse(responseCode = "201", description = "staff member created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StaffDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<StaffDto> createStaff(@RequestBody @Valid StaffDto staffDto){

        StaffDto savedStaff = staffService.createStaff(staffDto);

        return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all staff members",
            description = "Returns a list of all staff members",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of staff members",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = StaffDto.class)))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<List<StaffDto>> getAllStaff()
    {
        List<StaffDto> allStaff = staffService.getAllStaff();

        return new ResponseEntity<>(allStaff, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a staff member by ID",
            description = "Returns a staff member by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "staff member found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StaffDto.class))),
                    @ApiResponse(responseCode = "404", description = "Staff member not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable("id") Long staffId)
    {
        StaffDto foundStaff = staffService.getStaffById(staffId);

        return new ResponseEntity<>(foundStaff, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a staff member",
            description = "Updates and returns a staff member by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "staff member updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StaffDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Staff member not found", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<StaffDto> updateStaff(@RequestBody @Valid StaffDto staffDto, @PathVariable("id") Long staffId)
    {
        StaffDto updatedStaff = staffService.updateStaff(staffDto, staffId);

        return new ResponseEntity<>(updatedStaff, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a staff member",
            description = "Deletes a staff member by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Staff member with passed ID deleted successfully", content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "404", description = "Staff member not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable("id") Long staffId)
    {
        staffService.deleteStaff(staffId);

        return new ResponseEntity<>("Staff with ID : " + staffId + " deleted successfully", HttpStatus.OK);
    }
}
