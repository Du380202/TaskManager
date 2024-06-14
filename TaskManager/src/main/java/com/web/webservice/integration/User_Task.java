package com.web.webservice.integration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.web.webservice.api.TaskAPI;
import com.web.webservice.dto.TaskDto;

public class User_Task {
	public List<TaskDto> getAllByUserId(TaskAPI taskApi, Integer userId) {
		List<TaskDto> result = new ArrayList<TaskDto>();
		List<TaskDto> all = taskApi.getAll();
		for (TaskDto tmp : all) {
			if (tmp.getAssigneeId().equals(userId)) {
				result.add(tmp);
			}
		}
				
		return result;
	}
}
