package com.xrosstools.xstate.def;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.xrosstools.xstate.NullAction;
import com.xrosstools.xstate.StateMachineDiagramConstants;
import com.xrosstools.xstate.XstateSpring;

public class ActionDef implements StateMachineDiagramConstants {
	private static boolean enableSpring;
	
	static {
		try {
			Class.forName("org.springframework.context.ApplicationContext");
			enableSpring = true;
		} catch (ClassNotFoundException e) {
			enableSpring = false;
		}
	}

	private String implName;

	public ActionDef(String implName) {
		this.implName = implName;
	}
	
	public String getImplName() {
		return implName;
	}

	public void setImplName(String implName) {
		this.implName = implName;
	}
	
	public Object create(Class<? extends TriggerMethodWrapper> wrapperClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(implName == null || implName.length() == 0)
			return NullAction.instance;
		
		String className = implName;
		String methodName = null;
		if(implName.contains(SEPARATOR)) {
			String[] parts = implName.split(SEPARATOR);
			className = parts[0];
			methodName = parts[1];
		}
		
		Class<?> clazz = Class.forName(className);

		try {
			Object instance = null;
			if(enableSpring)
				instance = XstateSpring.getBean(className);
			
			if(instance == null)
				instance = clazz.getDeclaredConstructor().newInstance();
			
			if(methodName == null)
				return instance;

			TriggerMethodWrapper wrapper = wrapperClass.getDeclaredConstructor().newInstance();
			wrapper.instance = instance;
			wrapper.method = clazz.getDeclaredMethod(methodName, wrapper.parameterClasses);
			return wrapper;
		} catch(NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch(SecurityException e) {
			throw new RuntimeException(e);
		} catch(IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch(InvocationTargetException e) {
			throw new RuntimeException(e);
		}			
	}
}
