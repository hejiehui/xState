package com.xrosstools.xstate;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class XstateSpring implements ApplicationContextAware {
    private static volatile ApplicationContext applicationContext;

    public static void enable(ApplicationContext applicationContext) throws BeansException {
        if (XstateSpring.applicationContext == null) {
        	XstateSpring.applicationContext = applicationContext;
        }
    }
  
    public static Object getBean(String className) throws ClassNotFoundException {
    	if(applicationContext == null)
    		return null;

    	try {
    		return applicationContext.getBean(Class.forName(className));
    	}catch(BeansException e) {
    		return null;
    	}
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(XstateSpring.applicationContext == null)
			XstateSpring.applicationContext = applicationContext;
	}

}
