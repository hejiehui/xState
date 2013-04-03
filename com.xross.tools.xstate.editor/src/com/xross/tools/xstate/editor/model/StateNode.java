package com.xross.tools.xstate.editor.model;

import java.util.List;

public class StateNode {
	private String name;
	private String description;
	private String entryAction;
	private String existAction;
	private List<StateTransition> inputs;
	private List<StateTransition> outputs;
	
	
	public List<StateTransition> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<StateTransition> outputs) {
		this.outputs = outputs;
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
	public String getEntryAction() {
		return entryAction;
	}
	public void setEntryAction(String entryAction) {
		this.entryAction = entryAction;
	}
	public String getExistAction() {
		return existAction;
	}
	public void setExistAction(String existAction) {
		this.existAction = existAction;
	}
	public List<StateTransition> getInputs() {
		return inputs;
	}
	public void setInputs(List<StateTransition> inputs) {
		this.inputs = inputs;
	}
}
