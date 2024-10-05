package com.calculate.demo.Epression.repository;


import com.calculate.demo.Epression.entity.ExpressionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpressionRepository extends JpaRepository<ExpressionEntity,Long> {

    List<ExpressionEntity> findByDate(String date);

}
