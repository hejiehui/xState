package com.xrosstools.xstate.def;

import java.lang.reflect.Method;

import com.xrosstools.xstate.EntryAction;
import com.xrosstools.xstate.Event;
import com.xrosstools.xstate.ExitAction;
import com.xrosstools.xstate.TransitAction;
import com.xrosstools.xstate.TransitionGuard;

public class TriggerMethodWrapper {
	private static final Class<?>[] NODE_ACTION_PARAMS = new Class[] {String.class, Event.class};
	private static final Class<?>[] TRANSITION_ACTION_PARAMS = new Class[] {String.class, String.class, Event.class};

	protected Class<?>[] parameterClasses;

	protected Object instance;
	protected Method method;
	
	TriggerMethodWrapper(Class<?>[] parameterClasses) {
		this.parameterClasses = parameterClasses;
	}

	Object invoke(Object...params) {
		try {
			return method.invoke(instance, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static class EntryActionMethodWrapper extends TriggerMethodWrapper implements EntryAction {
		EntryActionMethodWrapper() {
			super(NODE_ACTION_PARAMS);
		}
		@Override
		public void enter(String targetStateId, Event event) {
			invoke(targetStateId, event);
		}
	}
	
	public static class ExitActionMethodWrapper extends TriggerMethodWrapper implements ExitAction {
		ExitActionMethodWrapper() {
			super(NODE_ACTION_PARAMS);
		}
		@Override
		public void exit(String targetStateId, Event event) {
			invoke(targetStateId, event);
		}
	}
	
	public static class TransitActionMethodWrapper extends TriggerMethodWrapper implements TransitAction {
		TransitActionMethodWrapper() {
			super(TRANSITION_ACTION_PARAMS);
		}
		public void transit(String sourceStateId, String targetStateId, Event event) {
			invoke(sourceStateId, targetStateId, event);
		}
	}
	
	public static class TransitionGuardMethodWrapper extends TriggerMethodWrapper implements TransitionGuard {
		TransitionGuardMethodWrapper() {
			super(TRANSITION_ACTION_PARAMS);
		}
		public boolean isTransitAllowed(String sourceId, String targetId, Event event) {
			return (boolean)invoke(sourceId, targetId, event);
		}
	}
}
