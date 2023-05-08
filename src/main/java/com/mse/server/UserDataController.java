package com.mse.server;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;

@RestController
@RequestMapping("/login")
public class UserDataController {
	@Autowired
	private UserDataRepository repo;
	
	@GetMapping(value="/find-all", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<UserData> findAll(){
		return repo.findAll();
	}
	
	// Sign in
	@PostMapping(value="/authentication", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String authentication(@RequestBody String json) {
		JSONObject jObject = new JSONObject(json);
		String loginId = jObject.getString("loginId");
		String loginPw = jObject.getString("loginPw");
		UserData u = repo.findByLoginId(loginId);
		jObject = new JSONObject();
		if(repo.existsByLoginId(loginId)) {
			if(u.getLoginPw().compareTo(loginPw) == 0)
				jObject.put("id", u.getId());
			else
				jObject.put("id", (long) -2);
		} else
			jObject.put("id", (long) -1);
		
		return jObject.toString();
	}
	
	@PostMapping(value="/post-user", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String postUser(@RequestBody String json) {
		Gson gson = new Gson();
		JSONObject jObject = new JSONObject(json);
		Long id = jObject.getLong("id");
		if(repo.existsById(id))
			return gson.toJson(repo.findById(id).get());
		else
			return gson.toJson(null);
	}
	
//	@PostMapping(value="/postDDL")

	// Sign up
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
