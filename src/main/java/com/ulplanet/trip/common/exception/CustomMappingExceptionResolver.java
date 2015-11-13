package com.ulplanet.trip.common.exception;

import com.ulplanet.trip.constant.BaseConstants;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 自定义映射 异常解析器.
 * @author zhangxd
 */
public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		Logger logger = LoggerFactory.getLogger(handler.getClass());
		logger.error("PrintStackTrace:", ex);
		
		// JSP格式返回
		if (!(request.getHeader("accept").contains("application/json")
				|| (request.getHeader("X-Requested-With") != null 
					&& request.getHeader("X-Requested-With").contains("XMLHttpRequest")))) {
			// Expose ModelAndView for chosen error view.
			String viewName = determineViewName(ex, request);
			if (viewName != null) {
				// 如果不是异步请求
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				
				return getModelAndView(viewName, ex, request);
			} else {
				return null;
			}
		} else { // JSON格式返回 
			try {
				
				Map<String, String> msgMap = new HashMap<>();
				msgMap.put(BaseConstants.RETURN_FIELD_STATUS, BaseConstants.STATUS_FAILURE);
				msgMap.put(BaseConstants.RETURN_FIELD_MESSAGE, "System Error!");
				
				response.getWriter().write(new Gson().toJson(msgMap));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}