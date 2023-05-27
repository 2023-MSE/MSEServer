package com.mse.server.obj;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

//import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@Table(name="userdata")
public class UserData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id;
	@Column(name="login_id", unique=true)
	@Expose
	private String loginId;
	@Column(name="login_pw")
	private String loginPw;
	@Column(unique=true)
	@Expose
	private String nickname;
	@Expose
	private int money;
	
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonIgnoreProperties({"owner"})
	@Expose
	private List<DungeonMap> maps;
	
	public UserData() { }

	public UserData(String loginId, String loginPw, String nickname) {
		super();
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.nickname = nickname;
		this.money = 0;
		this.maps = new ArrayList<DungeonMap>();
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

	public List<DungeonMap> getMaps() {
		return maps;
	}

	public void setMaps(List<DungeonMap> maps) {
		this.maps = maps;
	}	
	
	public void addMap(DungeonMap dm) {
		maps.add(dm);
	}
	
	public void updateMap(DungeonMap dm) {
		maps.set(dm.getId().intValue(), dm);
	}

	@Override
	public String toString() {
		return "UserData [id=" + id + ", loginId=" + loginId + ", loginPw=" + loginPw + ", nickname=" + nickname
				+ ", money=" + money + ", maps=" + maps + "]";
	}
}
