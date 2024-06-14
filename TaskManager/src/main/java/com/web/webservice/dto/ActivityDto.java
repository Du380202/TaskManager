package com.web.webservice.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {
    private Integer logId;
    private String action;
    private Date timestamp;
    private Integer userId;
    private Integer taskId;
    private String status;
}
