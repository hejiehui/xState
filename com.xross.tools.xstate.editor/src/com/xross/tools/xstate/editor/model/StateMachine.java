package com.xross.tools.xstate.editor.model;

import java.util.List;

public class StateMachine {
	private String name;
	private String description;

	private List<StateNode> nodes;
	private List<Event> events;
	
	public void removeNode(StateNode node){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<StateNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<StateNode> nodes) {
		this.nodes = nodes;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}
