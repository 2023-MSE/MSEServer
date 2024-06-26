package com.mse.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mse.server.obj.DungeonMap;
import com.mse.server.obj.Stage;
import com.mse.server.obj.UserData;
import com.mse.server.repository.DungeonMapRepository;
import com.mse.server.repository.StageRepository;
import com.mse.server.repository.UserDataRepository;

@RestController
@RequestMapping("/creator")
public class CreatorController {
	@Autowired
	private UserDataRepository userRepo;
	
	@Autowired
	private DungeonMapRepository mapRepo;
	
	@Autowired
	private StageRepository stageRepo;
	
	
	// createMap
	@PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String createMap(@RequestBody String json) {
		Gson gson = new Gson();
		JSONObject jObject = new JSONObject();
		DungeonMap m = gson.fromJson(json, DungeonMap.class);
		if(!userRepo.existsById(m.getUserId())) {
			System.out.println("No User.");
			jObject.put("success", false);
			return jObject.toString();
		}
		DungeonMap map = new DungeonMap(m.getName(), m.getCreatedTime(), m.getStages(), m.getUserId(), m.getNodeEditorJsonData());
		map = mapRepo.save(map);
//		System.out.println(map.getId());
		UserData u = userRepo.findById(map.getUserId()).get();
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();
		List<Stage> stgs = mapRepo.findById(map.getId()).get().getStages();
		for(Stage stg: m.getStages()) {
			stg = new Stage(stg.getId(), stg.getIdentifierId(), stg.getNextStage(), stg.getStageType(), stg.getSpecificTypeInfo(), stg.getLimitForElements(), stg.getMusicName(), stg.getMusicBytesData(), stg.getElements(), map.getId(), stg.getBpm());
			stg.setMowner(map);
			stg = stageRepo.save(stg);
			stgs.add(stg);
		}
		map.setOwner(u);
		map.setStages(stgs);
		maps.add(map);
		u.setMaps(maps);
		userRepo.save(u);
//		System.out.println(userRepo.findById(u.getId()).get());
//		System.out.println(userRepo.findById(u.getId()).get().getMaps());
//		System.out.println(mapRepo.findAll());
//		System.out.println(mapRepo.findById(map.getId()).get().getStages());
//		System.out.println(stageRepo.findAll());
		jObject.put("success", true);
		return jObject.toString();
	}
	
	// edit
	@PostMapping(value="/get-select-map", produces=MediaType.APPLICATION_JSON_VALUE)
	public String selectMap(@RequestBody String json) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		JSONObject jObject = new JSONObject(json);
		Long id = jObject.getLong("mapId");
		if(!mapRepo.existsById(id)) {
			System.out.println("No Map.");
			return gson.toJson(null);
		}
		DungeonMap m = mapRepo.findById(id).get();
		List<Stage> stgs = new ArrayList<Stage>();
		for(Stage stg: m.getStages()) {
			stgs.add(stg);
		}
//		System.out.println("stgs: " + stgs);
		String s = gson.toJson(stgs);
		return s;
	}
	
	// editMap
	@SuppressWarnings("deprecation")
	@PostMapping(value="/edit", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String editMap(@RequestBody String json) {
		Gson gson = new Gson();
		JSONObject jObject = new JSONObject();
		DungeonMap m = gson.fromJson(json, DungeonMap.class);
		if(!userRepo.existsById(m.getUserId())) {
			System.out.println("No User.");
			jObject.put("success", false);
			return jObject.toString();
		}
		
		UserData u = userRepo.findById(m.getUserId()).get();
		DungeonMap mm = mapRepo.getById(m.getId());
		
		List<Stage> stagesToRemove = new ArrayList<>();
		for (Stage stg : mm.getStages()) {
		    stagesToRemove.add(stageRepo.getById(stg.getId())); // 해당 요소에 대한 추가 작업 수행
		}
		mm.getStages().removeAll(stagesToRemove);
		
		/*for(Stage stg : mm.getStages()) {
			mm.getStages().remove(stg);
			stageRepo.delete(stageRepo.getById(stg.getId()));
		}*/
		
		List<Stage> stgs = new  ArrayList<Stage>();
		for(Stage stg: m.getStages()) {
			stg = new Stage(stg.getId(), stg.getIdentifierId(), stg.getNextStage(), stg.getStageType(), stg.getSpecificTypeInfo(), stg.getLimitForElements(), stg.getMusicName(), stg.getMusicBytesData(), stg.getElements(), m.getId(), stg.getBpm());
			stg.setMowner(mm);
			stg = stageRepo.save(stg); 
			stgs.add(stg);
			System.out.println("Added");
		}
		
		mm.setNodeEditorJsonData(m.getNodeEditorJsonData());
		mm.setStages(stgs);
		// Repository 에서 변수만 가져와서 바꿔줘도, 해당 변경사항이 Repository 에 반영되느냐 아니냐의 문제. => 안 되네. => 되네. 생각보다 훨씬 유도리가 잇었구나.
		
		mapRepo.save(mm);
		
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();	
		maps.set(m.getId().intValue()-1, mm);
		u.setMaps(maps);
		userRepo.save(u);
		
		jObject.put("success", true);
		return jObject.toString();
		
		
		/*
		mm = new DungeonMap(m.getId(), m.getName(), m.getCreatedTime(), m.getDeployed(), m.getOwner(), m.getStages(), m.getUserId(), m.getNodeEditorJsonData());
		for(Stage s : stgs) {
			Stage stg = stageRepo.getById(s.getId());
			stg = s;
			stgs.set(s.getId().intValue()-1, stg); // 여기서 오류 발생함.
		}
		mm.setStages(stgs);
		
		
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();		
		maps.set(m.getId().intValue()-1, mm); // 이 부분에서 오류 발생함.
		u.setMaps(maps);
		userRepo.save(u);
//		System.out.println(userRepo.findById(u.getId()).get());
//		System.out.println(userRepo.findById(u.getId()).get().getMaps());
//		System.out.println(mapRepo.findAll());
//		System.out.println(mapRepo.findById(m.getId()).get().getStages());
//		System.out.println(stageRepo.findAll());
		jObject.put("success", true);
		return jObject.toString();
		*/
	}
	
	// deleteMap
	@PostMapping(value="/delete", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String deleteMap(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		Long userId = jObject.getLong("userId");
		Long mapId = jObject.getLong("mapId");
		jObject = new JSONObject();
		if(!userRepo.existsById(userId)) {
			System.out.println("No User.");
			jObject.put("success", false);
			return jObject.toString();
		}
		if(!mapRepo.existsById(mapId)) {
			System.out.println("No Map.");
			jObject.put("success", false);
			return jObject.toString();
		}
		if(mapRepo.findById(mapId).get().getUserId() != userId) {
			System.out.println("No Result.");
			jObject.put("success", false);
			return jObject.toString();
		}
		UserData u = userRepo.findById(userId).get();
		DungeonMap m = mapRepo.findById(mapId).get();
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();
		List<Stage> stgs = stageRepo.findByMapId(m.getId());
		System.out.println(maps);
		System.out.println(stgs);
		for(Stage stg : stgs) {
			stageRepo.delete(stg);
		}
		maps.remove(m);
		mapRepo.delete(m);
//		System.out.println(userRepo.findById(u.getId()).get().getMaps());
//		System.out.println(mapRepo.findAll());
//		System.out.println(stageRepo.findAll());
		jObject.put("success", true);
		return jObject.toString();
	}
	
	// deployStateChange
	@PostMapping(value="/deploy-state-change", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String deployStateChange(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		Long userId = jObject.getLong("userId");
		Long mapId = jObject.getLong("mapId");
		jObject = new JSONObject();
		if(!userRepo.existsById(userId)) {
			System.out.println("No User.");
			jObject.put("success", false);
			return jObject.toString();
		}
		if(!mapRepo.existsById(mapId)) {
			System.out.println("No Map.");
			jObject.put("success", false);
			return jObject.toString();
		}
		if(mapRepo.findById(mapId).get().getUserId() != userId) {
			System.out.println("No Result.");
			jObject.put("success", false);
			return jObject.toString();
		} 
		UserData u = userRepo.findById(userId).get();
		List<DungeonMap> maps = mapRepo.findByUserId(u.getId());
		DungeonMap m = mapRepo.findById(mapId).get();
		int idx = maps.indexOf(m);
		m.changeDeployed();
		List<Stage> stgs = new ArrayList<Stage>();
		for(Stage stg: stageRepo.findByMapId(mapId)) {
			stgs.add(stg);
		}
		m.setStages(stgs);
		maps.set(idx, m);
		u.setMaps(maps);
		userRepo.save(u);
		jObject.put("success", true);
//		System.out.println(userRepo.findById(u.getId()).get().getMaps());
//		System.out.println(mapRepo.findAll());
		return jObject.toString();
	}
}
