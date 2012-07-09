package com.playlife.legcoresult.persistence.daos.generic;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

public class FinderIntroductionInterceptor implements IntroductionInterceptor {
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		JDOQL jdoql = methodInvocation.getMethod().getAnnotation(JDOQL.class);
		
    	Annotation[][] annotationsList = methodInvocation.getMethod().getParameterAnnotations();
    	Object[] arguments = methodInvocation.getArguments();
    	
    	Map<String, Object> map_param = new HashMap<String, Object>();
    	for (int i = 0; i < annotationsList.length; i++){
    		Annotation[] annotations = annotationsList[i];
    		if (annotations.length > 0 && annotations[0] instanceof P){
        		P annotation = (P) annotations[0];
        		map_param.put(annotation.name(), arguments[i]);	
    		}
    	}
    	
        IFinderExecutor<?> executor = (IFinderExecutor<?>) methodInvocation.getThis();
        
        String methodName = methodInvocation.getMethod().getName();
    	if (methodName.startsWith("find_")){
    		if (methodName.substring("find_".length()).startsWith("one_"))
    			return executor.executeFindOne(methodInvocation.getMethod(), (jdoql == null ? null : jdoql.value()), map_param);
    		else 
    			return executor.executeFindAll(methodInvocation.getMethod(), (jdoql == null ? null : jdoql.value()), map_param);
    	} else if (methodName.startsWith("count_")){
    		return executor.executeCount(methodInvocation.getMethod(), jdoql.value(), map_param);
    	}
        return methodInvocation.proceed();
    }

    @SuppressWarnings("rawtypes")
	public boolean implementsInterface(Class intf) {
        return intf.isInterface() && IFinderExecutor.class.isAssignableFrom(intf);
    }
}
