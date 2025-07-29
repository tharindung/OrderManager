package com.ccw.order_service.service;

import com.ccw.order_service.dto.StatusDto;

import java.util.List;

public interface StatusService {

    public StatusDto createStatus(StatusDto statusDto);

    public List<StatusDto> getAllStatuses();

    public StatusDto getStatusById(Integer statusId);

    public StatusDto updateStatus(StatusDto statusDto, Integer statusId);

    public void deleteStatus(Integer statusId);
}
