package com.chrisnor.koutye.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginForgetPasswordDto {
	
	private String email;
	private String oldPassword;
	private String newPassword;

}
