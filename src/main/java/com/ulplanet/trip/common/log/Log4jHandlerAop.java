package com.ulplanet.trip.common.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 日志处理.
 * Created by zhangxd
 * Date 2015年7月13日
 *
 */
public class Log4jHandlerAop {
	
	private static Logger logger = LoggerFactory.getLogger(Log4jHandlerAop.class);
	
	
	public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {
		
		
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		Object[] args = pjp.getArgs();
		StringBuilder sb = new StringBuilder();
		sb.append("className:").append(className).append("; ");
		sb.append("methodName:").append(methodName).append("; ");
		sb.append("args:[");
		for (int i = 0; i < args.length; i++) {
			sb.append("args[").append(i).append("]=").append(args[i]).append("; ");
		}
		
		if (args.length > 0) {
			int sbLength = sb.length();
			sb.delete(sbLength - 2, sbLength);
		}
		sb.append("];");
		
		logger.info(sb.toString());

		return pjp.proceed();
		
		
	}
}
