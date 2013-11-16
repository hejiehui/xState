package com.xross.tools.xstate.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class StateNode implements StateMachineConstants, IPropertySource {
	private String name;
	private String reference;
	private String description;
	private String entryAction;
	private String existAction;
	private List<StateTransition> inputs = new ArrayList<StateTransition>();
	private List<StateTransition> outputs = new ArrayList<StateTransition>();
	
	private Point location;
	private Dimension size;
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	protected void firePropertyChange(String propertyName){
		listeners.firePropertyChange(propertyName, null, null);
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
		listeners.firePropertyChange(PROP_LOCATION, null, location);
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
		listeners.firePropertyChange(PROP_SIZE, null, size);
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(PROP_NAME, name),
				new TextPropertyDescriptor(PROP_ENTRY_ACTION, entryAction),
				new TextPropertyDescriptor(PROP_EXIST_ACTION, existAction),
				new TextPropertyDescriptor(PROP_REFERENCE, reference),

			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_NAME.equals(propName))
			return name;
		if (PROP_ENTRY_ACTION.equals(propName))
			return entryAction;
		if (PROP_EXIST_ACTION.equals(propName))
			return existAction;
		if (PROP_REFERENCE.equals(propName))
			return reference;

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_NAME.equals(propName))
			setName((String)value);
		if (PROP_ENTRY_ACTION.equals(propName))
			setEntryAction((String)value);
		if (PROP_EXIST_ACTION.equals(propName))
			setExistAction((String)value);
		if (PROP_REFERENCE.equals(propName))
			setReference((String)value);
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}

	public void resetPropertyValue(Object propName){
	}
		
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
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public void removeOutput(StateTransition output){
		if(!outputs.contains(output))
			return;
		outputs.remove(output);
		output.getTarget().removeInput(output);
		firePropertyChange(PROP_OUTPUTS);
	}
	
	public void removeAllOutputs(){
		List<StateTransition> tempOutputs = new ArrayList<StateTransition>(outputs);
		for(StateTransition output:tempOutputs)
			removeOutput(output);
		firePropertyChange(PROP_OUTPUTS);
	}
	
	public void removeInput(StateTransition input){
		if(!inputs.contains(input))
			return;
		inputs.remove(input);
		input.getSource().removeOutput(input);
		firePropertyChange(PROP_INPUTS);
	}
	
	public void removeAllInputs(){
		List<StateTransition> tempInputs = new ArrayList<StateTransition>(inputs);
		for(StateTransition input:tempInputs)
			removeInput(input);
		firePropertyChange(PROP_INPUTS);
	}
	
	public void removeAllConnections(){
		removeAllInputs();
		removeAllOutputs();
	}
	
	public void addOutput(StateTransition output){
		outputs.add(output);
		firePropertyChange(PROP_OUTPUTS);
	}
	
	public void addInput(StateTransition input){
		inputs.add(input);
		firePropertyChange(PROP_INPUTS);
	}
	
	/**
	 * Helper method for remove links from a node
	 */
	public static void removeAllConnections(StateNode unit){
		if(unit != null)
			unit.removeAllConnections();
	}
	
	public static void removeAllInputs(StateNode unit){
		if(unit != null)
			unit.removeAllInputs();
	}
	
	public static void removeAllOutputs(StateNode unit){
		if(unit != null)
			unit.removeAllOutputs();
	}
}
