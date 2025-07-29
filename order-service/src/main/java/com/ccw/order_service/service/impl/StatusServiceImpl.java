package com.ccw.order_service.service.impl;

import com.ccw.order_service.dto.StatusDto;
import com.ccw.order_service.entity.Status;
import com.ccw.order_service.exception.ResourceNotFoundException;
import com.ccw.order_service.repository.StatusRepository;
import com.ccw.order_service.service.StatusService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StatusServiceImpl implements StatusService {

    private StatusRepository statusRepository;

    private ModelMapper modelMapper;

    @Override
    public StatusDto createStatus(StatusDto statusDto) {

        Status status = modelMapper.map(statusDto, Status.class);

        Status savedStatus = statusRepository.save(status);

        return modelMapper.map(savedStatus, StatusDto.class);
    }

    @Override
    public List<StatusDto> getAllStatuses() {
        return statusRepository.findAll().stream().map((status)->modelMapper.map(status, StatusDto.class)).collect(Collectors.toList());
    }

    @Override
    public StatusDto getStatusById(Integer statusId) {

        //Status foundStatus = statusRepository.findById(statusId).orElseThrow(()->new ResourceNotFoundException("Status with ID  : "+statusId+" does not exist !"));
        Status foundStatus = statusRepository.findById(statusId).orElseThrow(()->new ResourceNotFoundException("Status", "statusId", statusId.longValue()));

        return modelMapper.map(foundStatus, StatusDto.class);
    }

    @Override
    public StatusDto updateStatus(StatusDto statusDto, Integer statusId) {

        //Status foundStatus  = statusRepository.findById(statusId).orElseThrow(()->new ResourceNotFoundException("Status with ID  : "+statusId+" does not exist !"));
        Status foundStatus = statusRepository.findById(statusId).orElseThrow(()->new ResourceNotFoundException("Status", "statusId", statusId.longValue()));

        foundStatus.setStatus(statusDto.getStatus());

        Status updatedStatus = statusRepository.save(foundStatus);

        return modelMapper.map(updatedStatus, StatusDto.class);
    }

    @Override
    public void deleteStatus(Integer statusId) {

        //statusRepository.findById(statusId).orElseThrow(()->new ResourceNotFoundException("Status with ID  : "+statusId+" does not exist !"));
        statusRepository.findById(statusId).orElseThrow(()->new ResourceNotFoundException("Status", "statusId", statusId.longValue()));

        statusRepository.deleteById(statusId);
    }
}
