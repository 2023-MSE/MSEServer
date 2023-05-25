package com.mse.server;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/login")
public class UserDataController {
	@Autowired
	private UserDataRepository repo;
	
	@Autowired
	private DungeonMapRepository mapRepo;
	
	@GetMapping(value="/find-all", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<UserData> findAll(){
		List<UserData> datas = repo.findAll();
		for(UserData u : datas) {
			for (DungeonMap dm : u.getMaps()) {
				dm.setOwner(new UserData());
				dm.setStages(null);
			}
		}
		return datas;
	}
	
	/* Sign in */
	// authentication
	@PostMapping(value="/authentication", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String authentication(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		String loginId = jObject.getString("loginId");
		String loginPw = jObject.getString("loginPw");
		UserData u = repo.findByLoginId(loginId);
		jObject = new JSONObject();
		if(repo.existsByLoginId(loginId)) {
			if(u.getLoginPw().compareTo(loginPw) == 0) {
				jObject.put("id", u.getId());
				System.out.println("Login Success.");
			} else {
				jObject.put("id", (long) -2);
				System.out.println("Wrong Password.");
			}
		} else {
			jObject.put("id", (long) -1);
			System.out.println("Wrong Id.");
		}
		return jObject.toString();
	}
	
	// post user info
	@PostMapping(value="/post-user", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String postUser(@RequestBody String json) {
		Gson gson = new Gson();
		JSONObject jObject = new JSONObject(json);
		Long id = jObject.getLong("id");
		if(repo.existsById(id)) {
			repo.findById(id).get().setMaps(null);
			return gson.toJson(repo.findById(id).get());
		} else
			return gson.toJson(null);
	}
	
	@PostMapping(value="/post-ddl", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String deployedDungeonList() {
		Gson gson = new GsonBuilder().create();
		List<DungeonMap> maps = mapRepo.findByIsDeployed(true);
		List<DeployedMap> dms = new ArrayList<DeployedMap>();
		for(DungeonMap map : maps) {
			DeployedMap dm = new DeployedMap(map.getId(), map.getName(), map.getCreatedTime(), map.getUserId());
			dms.add(dm);
		}
		String ddl = gson.toJson(dms);
		return ddl;
	}
	
	@PostMapping(value="/post-dungeon-list", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String dungeonList(@RequestBody String json) {
		Gson gson = new GsonBuilder().create();
		JSONObject jObject = new JSONObject(json);
		Long id = jObject.getLong("mapId");
		List<DungeonMap> maps = mapRepo.findByUserId(id);
		List<CreatorMap> cms = new ArrayList<CreatorMap>();
		for(DungeonMap map : maps) {
			CreatorMap cm = new CreatorMap(map.getId(), map.getName(), map.getCreatedTime());
			cms.add(cm);
		}
		String dl = gson.toJson(cms);
		return dl;
	}

	/* Sign up */
	// double check
	@PostMapping(value="/double-check", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String doubleCheck(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		boolean isID = jObject.getBoolean("isId");
		String s = jObject.getString("s");
		jObject = new JSONObject();
		if(isID)
			jObject.put("isDouble", repo.existsByLoginId(s));
		else
			jObject.put("isDouble", repo.existsByNickname(s));
		return jObject.toString();
	}
	
	// sign up
	@PostMapping(value="/signup", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String signUp(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		String loginId = jObject.getString("loginId");
		String loginPw = jObject.getString("loginPw");
		String nickname = jObject.getString("nickname");
		jObject = new JSONObject();
		repo.save(new UserData(loginId, loginPw, nickname));
		if(repo.existsByLoginId(loginId))
			jObject.put("success", true);
		else
			jObject.put("success", false);
		return jObject.toString();
	}
	
}
