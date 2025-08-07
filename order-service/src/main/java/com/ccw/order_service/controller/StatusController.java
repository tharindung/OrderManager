package com.ccw.order_service.controller;

import com.ccw.order_service.dto.StatusDto;
import com.ccw.order_service.service.StatusService;
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
@RequestMapping("/order-service/apis/statuses")
@Tag(name="Statuses", description="CRUD operations for Statuses")
public class StatusController {

    private StatusService statusService;

    @Operation(
            summary = "Create a new status",
            description = "Creates and returns a new status",
            responses = {
                    @ApiResponse(responseCode = "201", description = "status created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StatusDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<StatusDto> createStatus(@RequestBody @Valid StatusDto statusDto)
    {
        StatusDto savedStatus = statusService.createStatus(statusDto);

        return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all statuses",
            description = "Returns a list of all statuses",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of statuses",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = StatusDto.class)))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<List<StatusDto>> getAllStatuses()
    {
        List<StatusDto> allStatuses = statusService.getAllStatuses();

        return new ResponseEntity<>(allStatuses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a status by ID",
            description = "Returns a status by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "status found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StatusDto.class))),
                    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<StatusDto> getStatusById(@PathVariable("id") Integer statusId)
    {
        StatusDto foundStatus = statusService.getStatusById(statusId);

        return new ResponseEntity<>(foundStatus, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a status",
            description = "Updates and returns a status by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "status updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StatusDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content)
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<StatusDto> updateStatus(@RequestBody @Valid StatusDto statusDto, @PathVariable("id") Integer statusId)
    {
        StatusDto updatedStatus = statusService.updateStatus(statusDto, statusId);

        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a status",
            description = "Deletes a status by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status with passed ID deleted successfully", content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable("id") Integer statusId)
    {
        statusService.deleteStatus(statusId);

        return new ResponseEntity<>("Status with ID : "+statusId+" deleted successfully !", HttpStatus.OK);
    }
}
