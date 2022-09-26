package com.xrosstools.xstate.idea.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;

public class StateMachineDiagram implements StateMachineConstants, IPropertySource {
	private String name;
	private String description;

	private List<StateMachine> machines = new ArrayList<StateMachine>();
	
	private boolean isHorizantal;
	private int verticalSpace = 50;
	private int horizantalSpace = 50;
	private float alignment = 0;
	private int nodeWidth = 100;
	private int nodeHeight = 50;	
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	protected void firePropertyChange(String propertyName){
		listeners.firePropertyChange(propertyName, null, null);
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(PROP_ID, PROP_ID),
				new TextPropertyDescriptor(PROP_DESRIPTION, PROP_DESRIPTION),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_ID.equals(propName))
			return getValue(name);
		if (PROP_DESRIPTION.equals(propName))
			return getValue(description);
		
		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_ID.equals(propName))
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

	private String getValue(String value) {
		return value == null? "" : value;
	}

	public List<StateMachine> getMachines() {
		return machines;
	}

	public void setMachines(List<StateMachine> machines) {
		this.machines = machines;
	}

	public void addMachine(int index, StateMachine machine) {
		machines.add(index, machine);
		firePropertyChange(PROP_LAYOUT);
	}

	public void removeMachine(StateMachine machine) {
		machines.remove(machine);
		firePropertyChange(PROP_LAYOUT);
	}
	
	public int indexOf(StateMachine machine) {
		return machines.indexOf(machine);
	}
	
	public void move(int newIndex, StateMachine machine) {
		int index = machines.indexOf(machine);

		if(index == newIndex || index+1 == newIndex)
		    return;

		if(index < newIndex)
			newIndex-=1;
		
		removeMachine(machine);
		addMachine(newIndex, machine);
		listeners.firePropertyChange(PROP_LAYOUT, null, null);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		firePropertyChange(PROP_ID);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		firePropertyChange(PROP_DESRIPTION);
	}

	public boolean isHorizantal() {
		return isHorizantal;
	}

	public void setHorizantal(boolean isHorizantal) {
		this.isHorizantal = isHorizantal;
	}

	public int getVerticalSpace() {
		return verticalSpace;
	}

	public void setVerticalSpace(int verticalSpace) {
		this.verticalSpace = verticalSpace;
	}

	public int getHorizantalSpace() {
		return horizantalSpace;
	}

	public void setHorizantalSpace(int horizantalSpace) {
		this.horizantalSpace = horizantalSpace;
	}

	public float getAlignment() {
		return alignment;
	}

	public void setAlignment(float alignment) {
		this.alignment = alignment;
	}

	public int getNodeWidth() {
		return nodeWidth;
	}

	public void setNodeWidth(int nodeWidth) {
		this.nodeWidth = nodeWidth;
	}

	public int getNodeHeight() {
		return nodeHeight;
	}

	public void setNodeHeight(int nodeHeight) {
		this.nodeHeight = nodeHeight;
	}
	
}
