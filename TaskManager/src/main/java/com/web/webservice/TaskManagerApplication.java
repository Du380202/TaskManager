package com.web.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

import com.web.webservice.api.TaskAPI;
import com.web.webservice.model.ResponseObject;

@SpringBootApplication
public class TaskManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
//		CallTaskAPI callTaskAPI = new CallTaskAPI();
//		ResponseObject test = callTaskAPI.callApiWithParameters("3");
//		System.out.println(test);
		
	}
}
