package com.mse.server;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {
	
	List<Stage> findByMownerId(Long mownerId);
	
}
