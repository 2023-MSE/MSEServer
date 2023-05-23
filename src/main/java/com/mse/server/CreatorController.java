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
	
	
	// createMap - dungeonmap객체 - boolean
	@PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean createMap(@RequestBody String json) {
		Gson gson = new Gson();
		DungeonMap m = gson.fromJson(json, DungeonMap.class);
		if(!userRepo.existsById(m.getUserId())) {
			System.out.println("No User.");
			return false;
		}
		UserData u = userRepo.findById(m.getUserId()).get();
		m = new DungeonMap(m.getName(), m.getCreatedTime(), m.getStages(), m.getUserId());
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();
		m = mapRepo.save(m);
		m.setOwner(u);
		maps.add(m);
		u.setMaps(maps);
		userRepo.save(u);
		System.out.println(userRepo.findById(u.getId()).get().getMaps());
		System.out.println(mapRepo.findAll());
		return true;
	}
	
	// edit
	@PostMapping(value="/get-select-map", produces=MediaType.APPLICATION_JSON_VALUE)
	public String selectMap(@RequestBody String json) {
		Gson gson = new GsonBuilder().create();
		
		// map id 받아서 찾기
		JSONObject jObject = new JSONObject(json);
		Long id = jObject.getLong("id");
		DungeonMap m = mapRepo.findById(id).get();

		m = new DungeonMap(m.getId(), m.getName(), m.getCreatedTime(), m.getOwner(), m.getStages(), m.getUserId());
		
		System.out.println("abc");
		System.out.println(m);
		
		// 음.. toJson에서 에러가 계속 남
		// 아마 map에서 owner가 user를 가리키고, user의 maps가 map을 가리키니까 오류가 나는 것 같음
		// 그래서 그냥 stage 정보만 보내줘도 괜찮지 않을까?
		String mm = gson.toJson(m);
//		gson.toJson(m);
		System.out.println(mm);
//		String mm = gson.toJson(m);
//		System.out.println(mm);
		// dungeonMap 객체 반환
		if(mapRepo.existsById(id)) {
//			return mm;
			return null;
		}
		else
			return gson.toJson(null);
	}
	
	// editMap
	@PostMapping(value="/edit", consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean editMap(@RequestBody String json) {
		Gson gson = new Gson();
		DungeonMap m = gson.fromJson(json, DungeonMap.class);
		if(!userRepo.existsById(m.getUserId())) {
			System.out.println("No User.");
			return false;
		}
		UserData u = userRepo.findById(m.getUserId()).get();
		m = new DungeonMap(m.getId(), m.getName(), m.getCreatedTime(), m.getStages(), m.getUserId());
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();
		m = mapRepo.save(m);
		m.setOwner(u);
		maps.set(m.getId().intValue()-1, m);
		u.setMaps(maps);
		userRepo.save(u);
		System.out.println(userRepo.findById(u.getId()).get().getMaps());
		System.out.println(mapRepo.findAll());
		return true;
	}
	
	// deleteMap - userdata id, map id - boolean
	@PostMapping(value="/delete", consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteMap(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		Long userId = jObject.getLong("userId");
		Long mapId = jObject.getLong("mapId");
		UserData u = userRepo.findById(userId).get();
		DungeonMap m = mapRepo.findById(mapId).get();
		List<DungeonMap> maps = userRepo.findById(u.getId()).get().getMaps();
		maps.remove(m);
		mapRepo.delete(m);
		System.out.println(userRepo.findById(u.getId()).get().getMaps());
		System.out.println(mapRepo.findAll());
		return true;
	}
	
	// deployStateChange - userdata id, map id - boolean	
	@PostMapping(value="/deploy-state-change", consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean deployStateChange(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		Long userId = jObject.getLong("userId");
		Long mapId = jObject.getLong("mapId");
		UserData u = userRepo.findById(userId).get();
		DungeonMap d = mapRepo.findById(mapId).get();
		return true;
	}
}
