package com.webservice.TaskService.serviceclient.implement;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.TaskService.dto.UserDto;
import com.webservice.TaskService.model.ResponseObject;
import com.webservice.TaskService.serviceclient.UserClient;

import reactor.core.publisher.Mono;


@Service
public class UserClientImpl implements UserClient {
	private final String url = "http://localhost:8080/api/user";
	private final WebClient.Builder builder = WebClient.builder();
	@Override
	public String getNameUser(Integer id) {
		String newUrl = url + "/fullName/" + id ;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		return (String) responseObject.getData();
	}

}
