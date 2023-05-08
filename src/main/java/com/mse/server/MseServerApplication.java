package com.mse.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MseServerApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MseServerApplication.class, args);
		
		UserDataManager mgr = ctx.getBean(UserDataManager.class);
		
		mgr.insert("abcde", "abcde", "abcde");
		
		System.out.println(mgr.nicknameDoubleCheck("abcde"));
		
		mgr.authentication("abcde", "abcde");
		mgr.authentication("aabcd", "abcde");
		mgr.authentication("abcde", "bcdef");
		
	}

}
