package com.mse.server;

public interface UserDataManager {
	// Login Controller
	Long authentication(String loginId, String loginPw);

	boolean nicknameDoubleCheck(String nickname);
	
	boolean insert(String loginId, String loginPw, String nickname);
	
	// Player Controller
	int updateMoney(Long id, int money);
}
