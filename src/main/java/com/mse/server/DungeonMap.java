package com.mse.server;

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
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="owner_id")
	private UserData owner;
	
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Stage> stages;
	
	public DungeonMap() { }

	public DungeonMap(String name, String createdTime, List<Stage> stages) {
		super();
		this.name = name;
		this.createdTime = createdTime;
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

	public List<Stage> getStages() {
		return stages;
	}
}
