package com.xross.tools.xstate;

import java.util.Map;

import com.xross.tools.xstate.def.StateMachineDef;

public class StateMachineDiagram {
	private String name;
	private String description;
	private Map<String, StateMachineDef> stateMachines;
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
	public Map<String, StateMachineDef> getStateMachines() {
		return stateMachines;
	}
	public void setStateMachines(Map<String, StateMachineDef> stateMachines) {
		this.stateMachines = stateMachines;
	}
	

	public StateMachine create(String stateMachineName){
		return null;
	}
}
