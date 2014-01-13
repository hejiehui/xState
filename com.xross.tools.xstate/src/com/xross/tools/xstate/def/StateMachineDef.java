package com.xross.tools.xstate.def;

import java.util.List;

public class StateMachineDef {
	private String name;
	private String description;
	private List<StateDef> states;
	private List<TransitionDef> tansitions;
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
	public List<StateDef> getStates() {
		return states;
	}
	public void setStates(List<StateDef> states) {
		this.states = states;
	}
	public List<TransitionDef> getTansitions() {
		return tansitions;
	}
	public void setTansitions(List<TransitionDef> tansitions) {
		this.tansitions = tansitions;
	}
	
}
