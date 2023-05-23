package com.mse.server;

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
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="mowner_id")
	private DungeonMap mowner;
	
	public Stage() { }

	public Stage(long nextStage, int stageType, String specificTypeInfo) {
		super();
		this.nextStage = nextStage;
		this.stageType = stageType;
		this.specificTypeInfo = specificTypeInfo;
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
}
