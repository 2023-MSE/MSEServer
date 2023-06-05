package com.mse.server.obj;

import java.util.List;

import com.google.gson.annotations.Expose;

import jakarta.persistence.*;

@Entity
@Table(name="stage")
public class Stage {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	private Long id;
	@Column(name="next_stage")
	@Expose
	private List<Long> nextStage;
	@Column(name="stage_type")
	@Expose
	private int stageType; // 1:Monster/2:Boss/3:Buff/4:Relax
	@Column(name="specific_type_info")
	@Expose
	private String specificTypeInfo;
	@Column(name="limit_for_elements")
	@Expose
	private short limitForElements;
	@Column(name="music_name")
	@Expose
	private String musicName;
	@Column(name="music_bytes_data")
	@Expose
	private byte[] musicBytesData;
	@Expose
	private List<Integer> elements;
	@Column(name="map_id")
	@Expose
	private long mapId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="mowner_id")
	private DungeonMap mowner;
	
	public Stage() { }

	public Stage(List<Long> nextStage, int stageType, String specificTypeInfo) {
		super();
		this.nextStage = nextStage;
		this.stageType = stageType;
		this.specificTypeInfo = specificTypeInfo;
	}

	public Stage(Long id, List<Long> nextStage, int stageType, String specificTypeInfo, DungeonMap mowner) {
		super();
		this.id = id;
		this.nextStage = nextStage;
		this.stageType = stageType;
		this.specificTypeInfo = specificTypeInfo;
		this.mowner = mowner;
	}
	
	

	public Stage(Long id, List<Long> nextStage, int stageType, String specificTypeInfo, short limitForElements, 
			String musicName, byte[] musicBytesData, List<Integer> elements, long mapId) {
		super();
		this.id = id;
		this.nextStage = nextStage;
		this.stageType = stageType;
		this.specificTypeInfo = specificTypeInfo;
		this.limitForElements = limitForElements;
		this.musicName = musicName;
		this.musicBytesData = musicBytesData;
		this.elements = elements;
		this.mapId = mapId;
	}
	
	public short getLimitForElements() {
		return limitForElements;
	}

	public void setLimitForElements(short limitForElements) {
		this.limitForElements = limitForElements;
	}

	public List<Long> getNextStage() {
		return nextStage;
	}

	public void setNextStage(List<Long> nextStage) {
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
	
	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public byte[] getMusicBytesData() {
		return musicBytesData;
	}

	public void setMusicBytesData(byte[] musicBytesData) {
		this.musicBytesData = musicBytesData;
	}

	public List<Integer> getElements() {
		return elements;
	}

	public void setElements(List<Integer> elements) {
		this.elements = elements;
	}

	public long getMapId() {
		return mapId;
	}

	public void setMapId(long mapId) {
		this.mapId = mapId;
	}

	@Override
	public String toString() {
		return "Stage [id=" + id + ", nextStage=" + nextStage + ", stageType=" + stageType + ", specificTypeInfo="
				+ specificTypeInfo + ", musicName=" + musicName + ", musicBytesData="
				+ musicBytesData + ", elements=" + elements + ", mapId=" + mapId + "]";
	}
}
