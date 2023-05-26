package com.mse.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mse.server.obj.UserData;
import com.mse.server.repository.UserDataRepository;

@Service
public class UserDataManagerImpl implements UserDataManager {

	@Autowired
	private UserDataRepository repo;
	
	@Override
	public Long authentication(String loginId, String loginPw) {
		UserData u = repo.findByLoginId(loginId);
		if(repo.existsByLoginId(loginId)) {
			if(u.getLoginPw() == loginPw) {
				// Correct id/password
				System.out.println("Login Success.");
				return u.getId();
			} else {
				// Wrong password
				System.out.println("Wrong Password.");
				return (long) -2;
			}
		} else {
			// Wrong id
			System.out.println("Wrong Id.");
			return (long) -1;
		}
	}

	@Override
	public boolean nicknameDoubleCheck(String nickname) {
		return repo.existsByNickname(nickname);
	}

	@Override
	public boolean insert(String loginId, String loginPw, String nickname) {
		repo.save(new UserData(loginId, loginPw, nickname));
		System.out.println("Sign Up Success.");
		return true;
	}

	@Override
	public int updateMoney(Long id, int money) {
		UserData u = repo.findById(id).get();
		return u.addMoney(money);
	}

}
