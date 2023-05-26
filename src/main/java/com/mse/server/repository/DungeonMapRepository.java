package com.mse.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mse.server.obj.DungeonMap;

public interface DungeonMapRepository extends JpaRepository<DungeonMap, Long> {
	List<DungeonMap> findByIsDeployed(boolean t);
	
	List<DungeonMap> findByUserId(Long id);
}
