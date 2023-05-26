package com.mse.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mse.server.obj.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
	
	boolean existsByLoginId(String loginId);
	
	UserData findByLoginId(String loginId);
	
	boolean existsByNickname(String nickname);	
}
