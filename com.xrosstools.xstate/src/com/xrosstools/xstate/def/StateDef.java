package com.xrosstools.xstate.def;

import com.xrosstools.xstate.StateType;

public class StateDef {
	private String id;
	private String description;
	private String reference;
	private StateType type;
	private ActionDef entryActionDef;
	private ActionDef exitActionDef;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public StateType getType() {
		return type;
	}
	public void setType(StateType type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public ActionDef getEntryActionDef() {
		return entryActionDef;
	}
	public void setEntryActionDef(ActionDef entryActionDef) {
		this.entryActionDef = entryActionDef;
	}
	public ActionDef getExitActionDef() {
		return exitActionDef;
	}
	public void setExitActionDef(ActionDef exitActionDef) {
		this.exitActionDef = exitActionDef;
	}
	
}
