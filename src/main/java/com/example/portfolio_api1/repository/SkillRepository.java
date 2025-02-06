package com.example.portfolio_api1.repository;

import com.example.portfolio_api1.entity.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository  extends JpaRepository<SkillEntity,Long> {
}
