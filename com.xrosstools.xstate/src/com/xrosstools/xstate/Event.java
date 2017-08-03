package com.xrosstools.xstate;

/**
 * Inherit this class if you want to pass additional context to actions.
 * 
 * @author He, Jiehui
 *
 */
public class Event {
	private String id;

	/**
	 * You must set id later.
	 */
	public Event() {
	}
	
    public Event(String id) {
        this.id = id;
    }
    
	public String getId() {
		return id;
	}
	
	/**
	 * In case you want to reuse same event object, be careful about racing condition.
	 * @param id
	 */
    public void setId(String id) {
        this.id = id;
    }
}