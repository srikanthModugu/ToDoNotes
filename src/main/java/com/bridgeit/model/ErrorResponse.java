package com.bridgeit.model;

import java.util.List;

import org.springframework.validation.FieldError;

public class ErrorResponse extends CustomeResponse {

	
	private List<FieldError> errorsList;

	public List<FieldError> getErrorsList() {
		return errorsList;
	}

	public void setErrorsList(List<FieldError> errorsList) {
		this.errorsList = errorsList;
	}
	
}
