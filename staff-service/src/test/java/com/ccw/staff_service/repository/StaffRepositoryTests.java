package com.ccw.staff_service.repository;

import com.ccw.staff_service.entity.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StaffRepositoryTests {

    @Autowired
    private StaffRepository staffRepository;

    private Staff staff;

    @BeforeEach
    public void setup()
    {
        staff = Staff.builder()
                .staffFirstName("Jane")
                .staffLastName("Doe")
                .staffEmail("jane.doe@gmail.com")
                .staffPassword("12345").build();
    }

    //Junit test for create staff operation
    @DisplayName("Junit test for create staff operation")
    @Test
    public void givenStaffObject_whenSave_thenReturnSavedStaff()
    {
        //Given - precondition

        //When - behavior we are testing
        Staff savedStaff = staffRepository.save(staff);

        //Then - verifying the output
        assertThat(savedStaff).isNotNull();
        assertThat(savedStaff.getStaffId()).isGreaterThan(0);
    }

    //Junit test for get all staff operation
    @DisplayName("Junit test for get all staff operation")
    @Test
    public void givenStaffList_whenFindAll_thenReturnStaffList()
    {
        //Given - precondition
        Staff staff1 = Staff.builder()
                .staffFirstName("Mark")
                .staffLastName("Smith")
                .staffEmail("mark.smith@gmail.com")
                .staffPassword("12345").build();

        staffRepository.save(staff);
        staffRepository.save(staff1);

        //When - behavior we are testing
        List<Staff> staffList = staffRepository.findAll();

        //Then - verifying the output
        assertThat(staffList).isNotNull();
        assertThat(staffList.size()).isEqualTo(2);
    }

    //Junit test for get staff by id operation
    @DisplayName("Junit test for get staff by id operation")
    @Test
    public void givenStaffObject_whenFindById_thenReturnStaffObject()
    {
        //Given - precondition

        staffRepository.save(staff);

        //When - behavior we are testing
        Staff foundStaff = staffRepository.findById(staff.getStaffId()).get();

        //Then - verifying the output
        assertThat(foundStaff).isNotNull();
    }

    //Junit test for update staff operation
    @DisplayName("Junit test for update staff operation")
    @Test
    public void givenStaffObject_whenUpdateStaff_thenReturnUpdatedStaffObject()
    {
        //Given - precondition
        staffRepository.save(staff);

        //When - behavior we are testing
        Staff foundStaff = staffRepository.findById(staff.getStaffId()).get();

        foundStaff.setStaffFirstName("Rob");
        foundStaff.setStaffLastName("Waugh");

        Staff updatedStaff = staffRepository.save(foundStaff);

        //Then - verifying the output
        assertThat(updatedStaff.getStaffFirstName()).isEqualTo("Rob");
        assertThat(updatedStaff.getStaffLastName()).isEqualTo("Waugh");
    }

    //Junit test for delete staff operation
    @DisplayName("Junit test for delete staff operation")
    @Test
    public void givenStaffObject_whenDelete_thenRemoveStaff()
    {
        //Given - precondition
        staffRepository.save(staff);

        //When - behavior we are testing
        staffRepository.deleteById(staff.getStaffId());

        Optional<Staff> staffOptional = staffRepository.findById(staff.getStaffId());

        //Then - verifying the output
        assertThat(staffOptional).isEmpty();
    }
}
