package com.web.webservice.api.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webservice.api.ProjectAPI;
import com.web.webservice.dto.ActivityDto;
import com.web.webservice.dto.ProjectDto;
import com.web.webservice.dto.TaskDto;
import com.web.webservice.model.ResponseObject;

import reactor.core.publisher.Mono;
@Service
public class ProjectImpl implements ProjectAPI {
	private final String url = "http://localhost:8080/api/project";
	private final WebClient.Builder builder = WebClient.builder();
	@Override
	public String callCountProject() {
		String newUrl = url + "/count";
		String responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
		return responseObject;
	}
	
	@Override
	public ProjectDto getById(Integer id) {
		String newUrl = url + "/detail?projectId=" + id;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		ObjectMapper mapper = new ObjectMapper();
        ProjectDto project = mapper.convertValue(responseObject.getData(), ProjectDto.class);
        return project;
	}
	@Override
	public List<ProjectDto> getAllProject() {
		String newUrl = url;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		List<ProjectDto> projects = (List<ProjectDto>) responseObject.getData();
		return projects;
	}

	@Override
	public boolean createNew(ProjectDto newProject) {
	    ResponseObject responseObject = builder.build()
	            .post()
	            .uri(url + "/create")
	            .body(Mono.just(newProject), ProjectDto.class)
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
	public boolean update(Integer id, ProjectDto project) {
		String newUrl = url + "/update/" + id;
		ResponseObject responseObject = builder.build()
	            .post()
	            .uri(newUrl)
	            .body(Mono.just(project), ProjectDto.class)
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
	public boolean updateProgress(Integer id, ProjectDto project) {
		String newUrl = url + "/updateProgress/" + id;
		ResponseObject responseObject = builder.build()
	            .post()
	            .uri(newUrl)
	            .body(Mono.just(project), ProjectDto.class)
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
	public List<ActivityDto> getActivities(Integer projectId) {
		String newUrl = url + "/activity/" + projectId;
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
	public List<ProjectDto> searchProject(String key) {
		String newUrl = url + "/searchProject?key=" + key;
		ResponseObject responseObject = builder.build()
				.get()
				.uri(newUrl)
                .retrieve()
                .bodyToMono(ResponseObject.class)
                .block();
		List<ProjectDto> projects = (List<ProjectDto>) responseObject.getData();
		return projects;
	}

	
	
}
