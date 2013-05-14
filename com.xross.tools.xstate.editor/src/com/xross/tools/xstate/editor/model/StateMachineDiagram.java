package com.xross.tools.xstate.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class StateMachineDiagram implements StateMachineConstants, IPropertySource {
	private String name;
	private String description;

	private List<StateMachine> machines = new ArrayList<StateMachine>();
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	protected void firePropertyChange(String propertyName){
		listeners.firePropertyChange(propertyName, null, null);
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(PROP_NAME, name),
				new TextPropertyDescriptor(PROP_DESRIPTION, description),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_NAME.equals(propName))
			return name;
		if (PROP_DESRIPTION.equals(propName))
			return description;
		
		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_NAME.equals(propName))
			setName((String)value);
		if (PROP_DESRIPTION.equals(propName))
			setDescription((String)value);
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}
	
	public void resetPropertyValue(Object propName){
	}

	public List<StateMachine> getMachines() {
		return machines;
	}

	public void setMachines(List<StateMachine> machines) {
		this.machines = machines;
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
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
	
}
