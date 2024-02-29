package com.enviro.assessment.grad001.motsoaledineotshoana.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.entity.ProductTypeEntity;


@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Integer> {
}