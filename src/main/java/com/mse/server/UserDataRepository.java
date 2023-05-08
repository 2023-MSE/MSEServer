package com.mse.server;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
	
	boolean existsByLoginId(String loginId);
	
	UserData findByLoginId(String loginId);
	
	boolean existsByNickname(String nickname);	
}
