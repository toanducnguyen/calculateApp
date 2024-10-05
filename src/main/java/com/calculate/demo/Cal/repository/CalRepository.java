package com.calculate.demo.Cal.repository;


import com.calculate.demo.Cal.entity.CalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalRepository extends JpaRepository<CalEntity,Long> {
    List<CalEntity> findByKeyA(String keyA);
}
