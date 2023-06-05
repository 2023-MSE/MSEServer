package com.mse.server.obj;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import jakarta.persistence.*;

@Entity
@Table(name="dungeonmap")
public class DungeonMap {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id;
	@Column(name="node_editor_json_data")
	@Expose
	private String nodeEditorJsonData;
	@Expose
	private String name;
	@Column(name="created_time")
	@Expose
	private String createdTime;
	@Expose
	private boolean isDeployed;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="owner_id")
	private UserData owner;
	
	@JoinColumn(name="user_id")
	@Expose
	private long userId;
	
	@OneToMany(mappedBy="mowner", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Expose
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
	
	public DungeonMap(Long id, String name, String createdTime, boolean isDeployed, UserData owner, List<Stage> stages, Long userId, String nodeEditorJsonData) {
		super();
		this.id = id;
		this.name = name;
		this.createdTime = createdTime;
		this.isDeployed = isDeployed;
		this.owner = owner;
		this.userId = userId;
		this.stages = stages;
		this.nodeEditorJsonData = nodeEditorJsonData;
	}

	public String getNodeEditorJsonData() {
		return nodeEditorJsonData;
	}

	public void setNodeEditorJsonData(String nodeEditorJsonData) {
		this.nodeEditorJsonData = nodeEditorJsonData;
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
		return "DungeonMap [id=" + id + ", name=" + name + ", deployed=" + isDeployed + ", createdTime=" + createdTime
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
