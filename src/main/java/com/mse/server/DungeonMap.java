package com.mse.server;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="dungeonmap")
public class DungeonMap {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(name="created_time")
	private String createdTime;
	private boolean isDeployed;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="owner_id")
//	@SerializedName("ownerId")
	private UserData owner;
	
	@JoinColumn(name="user_id")
	private long userId;
	
	@OneToMany(mappedBy="mowner", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Stage> stages;
	
	public DungeonMap() { }

	public DungeonMap(String name, String createdTime, List<Stage> stages) {
		super();
		this.name = name;
		this.createdTime = createdTime;
		this.stages = new ArrayList<Stage>();
	}
	
	public DungeonMap(String name, String createdTime, List<Stage> stages, Long userId) {
		super();
		this.name = name;
		this.createdTime = createdTime;
		this.stages = new ArrayList<Stage>();
		this.userId = userId;
	}
	
	public DungeonMap(Long id, String name, String createdTime, List<Stage> stages, Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.createdTime = createdTime;
		this.stages = new ArrayList<Stage>();
		this.userId = userId;
	}
	
	public DungeonMap(Long id, String name, String createdTime, boolean isDeployed, UserData owner, List<Stage> stages, Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.createdTime = createdTime;
		this.isDeployed = isDeployed;
		this.owner = owner;
		this.userId = userId;
		this.stages = stages;
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

	public Long getId() {
		return id;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

	public List<Stage> getStages() {
		return stages;
	}

	public UserData getOwner() {
		return owner;
	}

	public void setOwner(UserData owner) {
		this.owner = owner;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "DungeonMap [id=" + id + ", name=" + name + ", deployed=" + isDeployed + ", createdTime=" + createdTime + ", ownerId=" + owner.getId()
				+ ", userId=" + userId + ", stages=" + stages + "]";
	}

	public boolean getDeployed() {
		return isDeployed;
	}

	public void setDeployed(boolean isDeployed) {
		this.isDeployed = isDeployed;
	}

	public boolean changeDeployed() {
		isDeployed = !isDeployed;
		return isDeployed;
	}
	
}
