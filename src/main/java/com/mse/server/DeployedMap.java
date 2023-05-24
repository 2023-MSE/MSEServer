package com.mse.server;

public class DeployedMap {
	private Long id;
	private String name;
	private String createdTime;
	private long userId;
	
	public DeployedMap(Long id, String name, String createdTime, long userId) {
		super();
		this.id = id;
		this.name = name;
		this.createdTime = createdTime;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "SendMap [id=" + id + ", name=" + name + ", createdTime=" + createdTime + ", userId=" + userId + "]";
	}
}
