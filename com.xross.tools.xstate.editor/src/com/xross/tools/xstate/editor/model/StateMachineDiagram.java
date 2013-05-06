package com.xross.tools.xstate.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.List;

public class StateMachineDiagram implements IPropertySource {
	private List<StateMachine> machines;
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	public List<StateMachine> getMachines() {
		return machines;
	}

	public void setMachines(List<StateMachine> machines) {
		this.machines = machines;
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}
	
}
