package com.web.webservice.api.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webservice.api.UserAPI;
import com.web.webservice.dto.CreateUser;
import com.web.webservice.dto.TaskDetailDto;
import com.web.webservice.dto.TaskDto;
import com.web.webservice.dto.UserDto;
import com.web.webservice.model.ResponseObject;

import reactor.core.publisher.Mono;

@Service
public class UserImpl implements UserAPI {
	private final String url = "http://localhost:8080/api/user";
	private final WebClient.Builder builder = WebClient.builder();
	@Override
	public UserDto findByUsername(String username) {
		String newUrl = url + "/" + username;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		ObjectMapper mapper = new ObjectMapper();
        UserDto user = mapper.convertValue(responseObject.getData(), UserDto.class);
        return user;
	}
	
	@Override
	public UserDto getUserById(Integer userId) {
		String newUrl = url + "/userId/" + userId;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		ObjectMapper mapper = new ObjectMapper();
        UserDto user = mapper.convertValue(responseObject.getData(), UserDto.class);
        return user;
	}
	
	@Override
	public boolean createNew(UserDto user) {
		ResponseObject responseObject = builder.build()
                .post()
                .uri(url + "/create")
                .body(Mono.just(user), UserDto.class)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("API call error: " + errorBody)))
                )
                .bodyToMono(ResponseObject.class)
                .block();

		if (responseObject != null && responseObject.getData() instanceof Boolean && (Boolean) responseObject.getData()) {
	        return true;
	    } else {
	    	throw new RuntimeException("Invalid response from API");
	    }
	}
	@Override
	public List<UserDto> getUserByRole() {
		String newUrl = url + "/All";
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		List<UserDto> tasks = (List<UserDto>) responseObject.getData();
		return tasks;
	}
	@Override
	public boolean updateUser(Integer id, UserDto user) {
		ResponseObject responseObject = builder.build()
                .post()
                .uri(url + "/update/" + id)
                .body(Mono.just(user), UserDto.class)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("API call error: " + errorBody)))
                )
                .bodyToMono(ResponseObject.class)
                .block();

		if (responseObject != null && responseObject.getData() instanceof Boolean && (Boolean) responseObject.getData()) {
	        return true;
	    } else {
	    	throw new RuntimeException("Invalid response from API");
	    }
	}

	@Override
	public boolean chancePassword(Integer id,  String password) {
		ResponseObject responseObject = builder.build()
                .post()
                .uri(url + "/chance/" + id)
                .body(Mono.just(password), String.class)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("API call error: " + errorBody)))
                )
                .bodyToMono(ResponseObject.class)
                .block();

		if (responseObject != null && responseObject.getData() instanceof Boolean && (Boolean) responseObject.getData()) {
	        return true;
	    } else {
	    	throw new RuntimeException("Invalid response from API");
	    }
	}

	@Override
	public boolean deleteUser(Integer userId) {
		ResponseObject responseObject = builder.build()
                .post()
                .uri(url + "/delete/" + userId)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("API call error: " + errorBody)))
                )
                .bodyToMono(ResponseObject.class)
                .block();

		if (responseObject != null && responseObject.getData() instanceof Boolean && (Boolean) responseObject.getData()) {
	        return true;
	    } else {
	    	throw new RuntimeException("Invalid response from API");
	    }
	}

}
