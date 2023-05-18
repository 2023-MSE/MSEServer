package com.mse.server;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

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
		return true;
	}
	
	// edit
	@PostMapping(value="/get-select-map", produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean selectMap(@RequestBody String json) {
		// userdata id, map id 받아서 찾기
		JSONObject jObject = new JSONObject(json);
		String userId = jObject.getString("userId");
		String mapId = jObject.getString("mapId");
		
		// dungeonMap 객체 반환
		return true;
	}
	
	// editMap - userdata id, map id - boolean
	@PostMapping(value="/edit", consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean editMap(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		String userId = jObject.getString("userId");
		String mapId = jObject.getString("mapId");
		return true;
	}
	
	// deleteMap - userdata id, map id - boolean
	@PostMapping(value="/delete", consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteMap(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		String userId = jObject.getString("userId");
		String mapId = jObject.getString("mapId");
		return true;
	}
	
	// deployStateChange - userdata id, map id - boolean	
	@PostMapping(value="/deploy-state-change", consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean deployStateChange(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		String userId = jObject.getString("userId");
		String mapId = jObject.getString("mapId");
		return true;
	}
}
