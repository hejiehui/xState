package com.xross.tools.xstate;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.xross.tools.xstate.def.StateMachineDef;

public class StateMachineDiagram {
	private String name;
	private String description;
	private Map<String, StateMachineDef> stateMachines;
	
	public StateMachineDiagram(
			String name,
			String description,
			Map<String, StateMachineDef> stateMachines) {
		this.name = name;
		this.description = description;
		this.stateMachines = stateMachines;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Set<String> getStateMachineNames() {
		return stateMachines.keySet();
	}

	public String getStateMachineDescription(String name) {
		return stateMachines.get(name).getDescription();
	}
	
	public StateMachine create(String stateMachineName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		StateMachineDef def = stateMachines.get(stateMachineName);
		if(def == null)
			throw new NoSuchElementException(String.format("Can not found state machine definition for name: %s", stateMachineName));
		
		return def.create();
	}
}
