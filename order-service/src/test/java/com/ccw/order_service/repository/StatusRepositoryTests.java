package com.ccw.order_service.repository;

import com.ccw.order_service.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StatusRepositoryTests {

    @Autowired
    private StatusRepository statusRepository;

    private Status status;

    @BeforeEach
    public void setup()
    {
        status = Status.builder().status("Assigned").build();
    }

    //Junit test for create status operation
    @DisplayName("Junit test for create status operation")
    @Test
    public void givenStatusObject_whenSave_thenReturnSavedStatus()
    {
        //Given - precondition

        //When - behavior we are testing
        Status savedStatus = statusRepository.save(status);

        //Then - verifying the output
        assertThat(savedStatus).isNotNull();
        assertThat(savedStatus.getStatusId()).isGreaterThan(0);
    }

    //Junit test for get all statuses operation
    @DisplayName("Junit test for get all statuses operation")
    @Test
    public void givenStatusList_whenFindAll_thenReturnStatusList()
    {
        //Given - precondition
        Status status1 = Status.builder().status("Completed").build();

        statusRepository.save(status);
        statusRepository.save(status1);

        //When - behavior we are testing
        List<Status> statusList = statusRepository.findAll();

        //Then - verifying the output
        assertThat(statusList).isNotNull();
        assertThat(statusList.size()).isEqualTo(2);
    }

    //Junit test for get status by id operation
    @DisplayName("Junit test for get status by id operation")
    @Test
    public void givenStatusObject_whenFindById_thenReturnStatusObject()
    {
        //Given - precondition
        statusRepository.save(status);

        //When - behavior we are testing
        Status foundStatus = statusRepository.findById(status.getStatusId()).get();

        //Then - verifying the output
        assertThat(foundStatus).isNotNull();
    }

    //Junit test for update status operation
    @DisplayName("Junit test for update status operation")
    @Test
    public void givenStatusObject_whenUpdateStatus_thenReturnUpdatedStatusObject()
    {
        //Given - precondition
        statusRepository.save(status);

        //When - behavior we are testing
        Status foundStatus =statusRepository.findById(status.getStatusId()).get();

        foundStatus.setStatus("Pending");

        Status updatedStatus = statusRepository.save(foundStatus);

        //Then - verifying the output
        assertThat(updatedStatus.getStatus()).isEqualTo("Pending");
    }

    //Junit test for delete status operation
    @DisplayName("Junit test for delete status operation")
    @Test
    public void givenStatusObject_whenDelete_thenRemoveStatus()
    {
        //Given - precondition
        statusRepository.save(status);

        //When - behavior we are testing
        statusRepository.deleteById(status.getStatusId());

        Optional<Status> statusOptional = statusRepository.findById(status.getStatusId());

        //Then - verifying the output
        assertThat(statusOptional).isEmpty();
    }
}
