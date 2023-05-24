package com.mse.server;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DungeonMapRepository extends JpaRepository<DungeonMap, Long> {
	List<DungeonMap> findByIsDeployed(boolean t);
}
