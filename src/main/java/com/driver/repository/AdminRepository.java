package com.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.driver.model.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
    @Query(value = "DELETE FROM admin c WHERE c.admin_id = ?1",
            nativeQuery = true)
    public void delete(Integer adminId);
}
