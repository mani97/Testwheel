package com.testwheel.test_service.Repository;


import com.testwheel.test_service.Dto.TestEntityDto;
import com.testwheel.test_service.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface TestRepository extends JpaRepository<TestEntity, Integer> {

}
