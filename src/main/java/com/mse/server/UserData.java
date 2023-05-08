package com.mse.server;

//import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@Table(name="userdata")
public class UserData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="login_id", unique=true)
	private String loginId;
	@Column(name="login_pw")
	private String loginPw;
	@Column(unique=true)
	private String nickname;
	private int money;
	
//	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//	private ArrayList<DungeonMap> maps;
	
	public UserData() { }

	public UserData(String loginId, String loginPw, String nickname) {
		super();
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.nickname = nickname;
		this.money = 0;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getId() {
		return id;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public int getMoney() {
		return money;
	}

	public int addMoney(int money) {
		this.money += money;
		return this.money;
	}
}
