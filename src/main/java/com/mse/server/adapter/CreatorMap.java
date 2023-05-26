package com.mse.server.adapter;

public class CreatorMap {
	private Long id;
	private String name;
	private String createdTime;
	
	public CreatorMap(Long id, String name, String createdTime) {
		super();
		this.id = id;
		this.name = name;
		this.createdTime = createdTime;
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

	@Override
	public String toString() {
		return "SendMap [id=" + id + ", name=" + name + ", createdTime=" + createdTime +  "]";
	}
}
