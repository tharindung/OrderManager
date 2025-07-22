package com.ccw.order_service.controller;

import com.ccw.order_service.dto.StatusDto;
import com.ccw.order_service.service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/order-service/apis/statuses")
public class StatusController {

    private StatusService statusService;

    @PostMapping
    public ResponseEntity<StatusDto> createStatus(@RequestBody StatusDto statusDto)
    {
        StatusDto savedStatus = statusService.createStatus(statusDto);

        return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StatusDto>> getAllStatuses()
    {
        List<StatusDto> allStatuses = statusService.getAllStatuses();

        return new ResponseEntity<>(allStatuses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StatusDto> getStatusById(@PathVariable("id") Integer statusId)
    {
        StatusDto foundStatus = statusService.getStatusById(statusId);

        return new ResponseEntity<>(foundStatus, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<StatusDto> updateStatus(@RequestBody StatusDto statusDto, @PathVariable("id") Integer statusId)
    {
        StatusDto updatedStatus = statusService.updateStatus(statusDto, statusId);

        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable("id") Integer statusId)
    {
        statusService.deleteStatus(statusId);

        return new ResponseEntity<>("Status with ID : "+statusId+" deleted successfully !", HttpStatus.OK);
    }
}
