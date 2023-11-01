package com.xrosstools.xstate.def;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.xrosstools.xstate.EntryAction;
import com.xrosstools.xstate.ExitAction;
import com.xrosstools.xstate.State;
import com.xrosstools.xstate.StateMachine;
import com.xrosstools.xstate.StateMachineFactory;
import com.xrosstools.xstate.TransitAction;
import com.xrosstools.xstate.Transition;
import com.xrosstools.xstate.TransitionGuard;

public class StateMachineDef {
	private String name;
    private String description;
    private List<StateDef> stateDefs;
    private List<EventDef> eventDefs;
    private List<TransitionDef> tansitionDefs;
	
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
	
    public List<StateDef> getStateDefs() {
		return stateDefs;
	}
	
	public void setStateDefs(List<StateDef> stateDefs) {
		this.stateDefs = stateDefs;
	}
	
	public List<EventDef> getEventDefs() {
		return eventDefs;
	}
	
	public void setEventDefs(List<EventDef> eventDefs) {
		this.eventDefs = eventDefs;
	}
	
	public List<TransitionDef> getTansitionDefs() {
		return tansitionDefs;
	}
	
	public void setTansitionDefs(List<TransitionDef> tansitionDefs) {
		this.tansitionDefs = tansitionDefs;
	}
    
	public StateMachine create(StateMachineFactory factory) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Map<String, List<Transition>> transitions = new HashMap<String, List<Transition>>();
		
		for(TransitionDef tansitionDef: tansitionDefs) {
			String sourceId = tansitionDef.getSourceId();
			if(!transitions.containsKey(sourceId))
				transitions.put(sourceId, new LinkedList<Transition>());
			List<Transition> outputs = transitions.get(sourceId);
			
			Transition transition = new Transition(
					tansitionDef.getEventDef().getId(), 
					(TransitionGuard)tansitionDef.getTransitGuardDef().create(),
					(TransitAction)tansitionDef.getTransitActionDef().create(), 
					tansitionDef.getSourceId(), 
					tansitionDef.getTargetId());
			
			outputs.add(transition);
		}
		
		List<State> states = new LinkedList<State>();
		for(StateDef stateDef: stateDefs) {
			states.add(new State(
					stateDef.getId(),
					stateDef.getReference(),
					factory,
					stateDef.getType(), 
					stateDef.getDescription(),
					(EntryAction)stateDef.getEntryActionDef().create(),
					(ExitAction)stateDef.getExitActionDef().create(),
					transitions.get(stateDef.getId())));
		}
	
		
		return new StateMachine(name, description, states);
	}
}
