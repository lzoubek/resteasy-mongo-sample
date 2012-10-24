package com.github.resteasy.mongo.sample.model;

public class StatusResponse {

	private Result result;
	private String message;
	
	public static StatusResponse success() {
		return new StatusResponse(Result.OK, null);
	}
	public static StatusResponse success(String message) {
		return new StatusResponse(Result.OK, message);
	}
	public static StatusResponse error(String message) {
		return new StatusResponse(Result.ERROR, message);
	}
	
	public StatusResponse() {
	}
	public StatusResponse(Result result, String message) {
		this.result = result;
		this.message = message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	public Result getResult() {
		return result;
	}
	public enum Result {
		OK,
		ERROR
	}
}
