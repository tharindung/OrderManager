package com.ccw.staff_service.repository;

import com.ccw.staff_service.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {

}
