package com.trader.services.bittrex.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Response<Result> {
	private Boolean success;
	private String message;
	private Result result;

	public String getMessage() {
		return message;
	}

	public Result getResult() {
		return result;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
