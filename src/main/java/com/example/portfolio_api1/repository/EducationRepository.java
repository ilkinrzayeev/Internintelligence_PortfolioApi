package com.example.portfolio_api1.repository;

import com.example.portfolio_api1.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity,Long> {
}
