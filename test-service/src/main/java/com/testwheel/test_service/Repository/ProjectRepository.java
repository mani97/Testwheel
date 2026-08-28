package com.testwheel.test_service.Repository;



import com.testwheel.test_service.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
