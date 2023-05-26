package com.mse.server;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name="stage")
public class Stage {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="next_stage")
	private long nextStage;
	@Column(name="stage_type")
	private int stageType; // 1:Monster/2:Boss/3:Buff/4:Relax
	@Column(name="specific_type_info")
	private String specificTypeInfo;
	
	// List<usigned int> elements
	
	
	// musicName
	
	
	// musicBytesData
	
	
	// 
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="mowner_id")
	@JsonIgnore
	private DungeonMap mowner;
	
	public Stage() { }

	public Stage(long nextStage, int stageType, String specificTypeInfo) {
		super();
		this.nextStage = nextStage;
		this.stageType = stageType;
		this.specificTypeInfo = specificTypeInfo;
	}

	public Stage(Long id, long nextStage, int stageType, String specificTypeInfo, DungeonMap mowner) {
		super();
		this.id = id;
		this.nextStage = nextStage;
		this.stageType = stageType;
		this.specificTypeInfo = specificTypeInfo;
		this.mowner = mowner;
	}

	public long getNextStage() {
		return nextStage;
	}

	public void setNextStage(long nextStage) {
		this.nextStage = nextStage;
	}

	public int getStageType() {
		return stageType;
	}

	public void setStageType(int stageType) {
		this.stageType = stageType;
	}

	public String getSpecificTypeInfo() {
		return specificTypeInfo;
	}

	public void setSpecificTypeInfo(String specificTypeInfo) {
		this.specificTypeInfo = specificTypeInfo;
	}
	
	public DungeonMap getMowner() {
		return mowner;
	}

	public void setMowner(DungeonMap mowner) {
		this.mowner = mowner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Stage [id=" + id + ", nextStage=" + nextStage + ", stageType=" + stageType + ", specificTypeInfo="
				+ specificTypeInfo + ", mowner=" + mowner.getId() + "]";
	}
}
