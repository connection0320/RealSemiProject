package com.fri.model;

public class MesDTO {
	int message_num;
	int message_request;
	int message_response;
	String message_content;
	String requestName;
	String responseName;
	
	
	public String getRequestName() {
		return requestName;
	}
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}
	public String getResponseName() {
		return responseName;
	}
	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}
	public int getMessage_num() {
		return message_num;
	}
	public void setMessage_num(int message_num) {
		this.message_num = message_num;
	}
	public int getMessage_request() {
		return message_request;
	}
	public void setMessage_request(int message_request) {
		this.message_request = message_request;
	}
	public int getMessage_response() {
		return message_response;
	}
	public void setMessage_response(int message_response) {
		this.message_response = message_response;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
}
