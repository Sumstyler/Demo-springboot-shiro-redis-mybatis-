package com.study.demo.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.study.demo.constant.ApplicationEnum;
import com.study.demo.vo.ResultVO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = ApplicationException.class)
	public ResultVO allExceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
		exception.printStackTrace();
		logger.error(exception.getMessage());
		ApplicationException applicationException = (ApplicationException) exception;
		return new ResultVO(applicationException.getApplicationEnum());
	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResultVO methodNotSupportedException(HttpServletRequest request, Exception exception) {
		exception.printStackTrace();
		logger.error(exception.getMessage());
		return new ResultVO(ApplicationEnum.PATH_ERROR);
	}

	@ExceptionHandler(value = UnauthorizedException.class)
	public ResultVO unauthorizedException(HttpServletRequest request, Exception exception) {
		exception.printStackTrace();
		logger.error(exception.getMessage());
		return new ResultVO(ApplicationEnum.UNAUTHORIZED);
	}

	@ExceptionHandler(value = Exception.class)
	public ResultVO defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		exception.printStackTrace();
		logger.error(exception.getMessage());
		if (exception instanceof NoHandlerFoundException) {
			return new ResultVO(ApplicationEnum.PATH_ERROR);
		} else {
			return new ResultVO(ApplicationEnum.FAIL);
		}
	}

}
