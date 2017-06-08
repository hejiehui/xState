package com.xrosstools.xstate.sample;

import com.xrosstools.xstate.Event;
import com.xrosstools.xstate.StateMachine;
import com.xrosstools.xstate.StateMachineFactory;

public class DbHealthyTest {
	public static void main(String[] args) {
		try {
			StateMachineFactory f = StateMachineFactory.load("sample.xstate");
			StateMachine sm = f.create("DB Health Lifecycle");
			
			System.out.println("Start state: " + sm.getCurrentState().getId());
			notify(sm, "initialize");
			
			notify(sm, "markdown");
			
			notify(sm, "markup");
			
			notify(sm, "shutdown");
			
			System.out.println("End state: " + sm.getCurrentState().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void notify(StateMachine sm, String eventId) {
		sm.notify(new Event(eventId));
		System.out.println(sm.getCurrentState().getId());
	}
}
