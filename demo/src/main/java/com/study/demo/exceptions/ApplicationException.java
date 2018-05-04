package com.study.demo.exceptions;

import com.study.demo.constant.ApplicationEnum;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ApplicationEnum applicationEnum;

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(ApplicationEnum applicationEnum) {
		this.applicationEnum = applicationEnum;
	}

	public ApplicationEnum getApplicationEnum() {
		return applicationEnum;
	}

}
