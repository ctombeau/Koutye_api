package com.chrisnor.koutye.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiError {
	private Integer errorcode;
	private String errorDesc;
	private LocalDateTime date;
}
