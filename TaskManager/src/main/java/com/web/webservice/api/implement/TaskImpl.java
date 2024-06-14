package com.web.webservice.api.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webservice.api.TaskAPI;
import com.web.webservice.dto.ActivityDto;
import com.web.webservice.dto.ProjectDto;
import com.web.webservice.dto.TaskDetailDto;
import com.web.webservice.dto.TaskDto;
import com.web.webservice.dto.UserDto;
import com.web.webservice.model.ResponseObject;

import reactor.core.publisher.Mono;
@Service
public class TaskImpl implements TaskAPI {
	private final String url = "http://localhost:8080/api/task";
	private final WebClient.Builder builder = WebClient.builder();
	@Override
	public List<TaskDetailDto> getDataTaskDetail() {
		String newUrl = url + "/details";
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		List<TaskDetailDto> tasks = (List<TaskDetailDto>) responseObject.getData();
		return tasks;
	}
	@Override
	public boolean createNew(TaskDto newTask) {
		ResponseObject responseObject = builder.build()
	            .post()
	            .uri(url + "/create")
	            .body(Mono.just(newTask), ProjectDto.class)
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
	public boolean update(Integer taskId, TaskDto updateTask) {
		ResponseObject responseObject = builder.build()
                .post()
                .uri(url + "/update/" + taskId)
                .body(Mono.just(updateTask), TaskDto.class)
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
	public TaskDto getTaskById(Integer taskId) {
		String newUrl = url + "/detail?taskId=" + taskId;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		ObjectMapper mapper = new ObjectMapper();
        TaskDto task = mapper.convertValue(responseObject.getData(), TaskDto.class);
        return task;
	}
	@Override
	public List<ActivityDto> getActivities(Integer taskId) {
		String newUrl = url + "/activity/" + taskId;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		List<ActivityDto> activities = (List<ActivityDto>) responseObject.getData();
		return activities;
	}
	@Override
	public List<TaskDto> getAll() {
		ResponseObject responseObject = builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block(); // Note: This is a blocking call and should be handled carefully

		ObjectMapper objectMapper = new ObjectMapper();
        List<TaskDto> tasks = objectMapper.convertValue(
                responseObject.getData(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, TaskDto.class)
        );
        
        return tasks;
	}
	@Override
	public List<TaskDto> getTaskByUser(Integer userId) {
		String newUrl = url + "/taskByUser/" + userId;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		List<TaskDto> tasks = (List<TaskDto>) responseObject.getData();
		return tasks;
	}
	@Override
	public boolean updateTask(Integer taskId, TaskDto updateTask) {
		ResponseObject responseObject = builder.build()
                .post()
                .uri(url + "/employee/update/" + taskId)
                .body(Mono.just(updateTask), TaskDto.class)
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
	public List<TaskDto> getTaskByProject(Integer projectId) {
		String newUrl = url + "/project/" + projectId;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		List<TaskDto> tasks = (List<TaskDto>) responseObject.getData();
		return tasks;
	}
	@Override
	public Integer getCountProcess() {
		String newUrl = url + "/countInprocess";
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		return (Integer) responseObject.getData();
	}

	@Override
	public Integer getCountCompleted() {
		String newUrl = url + "/countCompleted";
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		return (Integer) responseObject.getData();
	}
	@Override
	public Integer getCountCancell() {
		String newUrl = url + "/countCancel";
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		return (Integer) responseObject.getData();
	}
	@Override
	public List<TaskDetailDto> searchTask(String key) {
		String newUrl = url + "/searchTask?key=" + key;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		List<TaskDetailDto> tasks = (List<TaskDetailDto>) responseObject.getData();
		return tasks;
	}
	
	

}
