package net.course.domain;

public class Result {
	private boolean valid;
	private String errorMessage;
	
	public Result() {}
	
	public Result(boolean valid) {
		this(true, null);
	}
	
	private Result(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
	}
	
	public static Result ok() {
		return new Result(true);
	}
	
	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
