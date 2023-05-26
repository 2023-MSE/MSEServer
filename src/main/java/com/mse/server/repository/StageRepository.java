package com.mse.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mse.server.obj.Stage;

public interface StageRepository extends JpaRepository<Stage, Long> {
	
	List<Stage> findByMapId(Long mapId);
	
	
	
}
