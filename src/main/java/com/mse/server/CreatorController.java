package com.mse.server;

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
		System.out.println(m.getUserId());
		UserData u = userRepo.findById(m.getUserId()).get();
		m = new DungeonMap(m.getName(), m.getCreatedTime(), m.getStages(), m.getUserId());
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();
		m = mapRepo.save(m);
		m.setOwner(u);
		Stage stg = new Stage(1, 1, "a");
		stg.setMowner(m);
		List<Stage> stgs = mapRepo.findById(m.getId()).get().getStages();
		stgs.add(stg);
		stageRepo.save(stg);
		stg = new Stage(2, 1, "b");
		stg.setMowner(m);
		stgs.add(stg);
		stageRepo.save(stg);
		m.setStages(stgs);
		
		maps.add(m);
		u.setMaps(maps);
		userRepo.save(u);
		System.out.println(userRepo.findById(u.getId()).get().getMaps());
		System.out.println(mapRepo.findAll());
		System.out.println(mapRepo.findById(m.getId()).get().getStages());
		System.out.println(stageRepo.findAll());
		jObject.put("success", true);
		return jObject.toString();
	}
	
	// edit
	@PostMapping(value="/get-select-map", produces=MediaType.APPLICATION_JSON_VALUE)
	public String selectMap(@RequestBody String json) {
		Gson gson = new GsonBuilder().create();
		JSONObject jObject = new JSONObject(json);
		Long id = jObject.getLong("mapId");
		if(!mapRepo.existsById(id)) {
			System.out.println("No Map.");
			return gson.toJson(null);
		}
		DungeonMap m = mapRepo.findById(id).get();
		List<Stage> stgs = stageRepo.findByMownerId(id); 
		for(Stage stg : m.getStages()) {
			stg.setMowner(new DungeonMap());
		}
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
		List<Stage> stgs = stageRepo.findByMownerId(m.getId());
		DungeonMap mm = mapRepo.getById(m.getId());
		mm = new DungeonMap(m.getId(), m.getName(), m.getCreatedTime(), m.getDeployed(), m.getOwner(), m.getStages(), m.getUserId());
		mm.setOwner(u);
		for(Stage s : stgs) {
			Stage stg = stageRepo.getById(s.getId());
			stg = s;
			stgs.set(s.getId().intValue()-1, stg);
		}
		mm.setStages(stgs);
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();		
		maps.set(m.getId().intValue()-1, mm);
		u.setMaps(maps);
		userRepo.save(u);
		System.out.println(userRepo.findById(u.getId()).get().getMaps());
		System.out.println(mapRepo.findAll());
		System.out.println(mapRepo.findById(m.getId()).get().getStages());
		System.out.println(stageRepo.findAll());
		jObject.put("success", true);
		return jObject.toString();
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
		UserData u = userRepo.findById(userId).get();
		DungeonMap m = mapRepo.findById(mapId).get();
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();
		List<Stage> stgs = stageRepo.findByMownerId(m.getId());
		for(Stage stg : stgs) {
			stgs.remove(stg);
			stageRepo.delete(stg);
		}
		maps.remove(m);
		mapRepo.delete(m);
		System.out.println(userRepo.findById(u.getId()).get().getMaps());
		System.out.println(mapRepo.findAll());
		System.out.println(stageRepo.findAll());
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
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();
		DungeonMap m = mapRepo.findById(mapId).get();
		System.out.println("maps: " + maps.size() + ", map: " + maps.indexOf(m));
		int idx = maps.indexOf(m);
		m.changeDeployed();
		maps.set(idx, m);
		u.setMaps(maps);
		userRepo.save(u);
		jObject.put("success", true);
		System.out.println(userRepo.findById(u.getId()).get().getMaps());
		System.out.println(mapRepo.findAll());
		return jObject.toString();
	}
}
