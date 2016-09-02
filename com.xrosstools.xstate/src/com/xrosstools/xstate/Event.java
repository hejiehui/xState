package com.xrosstools.xstate;

/**
 * Inherit this class if you want to pass additional context to actions.
 * 
 * @author He, Jiehui
 *
 */
public class Event {
	private String id;

	public Event(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
}