package com.xrosstools.xstate.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class StateNode implements StateMachineConstants, IPropertySource {
	private String id;
	private String reference;
	private String description;
	private String entryAction;
	private String exitAction;
	private List<StateTransition> inputs = new ArrayList<StateTransition>();
	private List<StateTransition> outputs = new ArrayList<StateTransition>();
	
	private Point location;
	
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

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor(PROP_ID, PROP_ID),
				new TextPropertyDescriptor(PROP_ENTRY_ACTION, PROP_ENTRY_ACTION),
				new TextPropertyDescriptor(PROP_EXIT_ACTION, PROP_EXIT_ACTION),
				new TextPropertyDescriptor(PROP_REFERENCE, PROP_REFERENCE),
			};
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_ID.equals(propName))
			return getValue(id);
		if (PROP_ENTRY_ACTION.equals(propName))
			return getValue(entryAction);
		if (PROP_EXIT_ACTION.equals(propName))
			return getValue(exitAction);
		if (PROP_REFERENCE.equals(propName))
			return getValue(reference);

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_ID.equals(propName))
			setId((String)value);
		if (PROP_ENTRY_ACTION.equals(propName))
			setEntryAction((String)value);
		if (PROP_EXIT_ACTION.equals(propName))
			setExitAction((String)value);
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
	
	private String getValue(String value) {
		return value == null? "" : value;
	}
		
	public List<StateTransition> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<StateTransition> outputs) {
		this.outputs = outputs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		firePropertyChange(PROP_ID);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		firePropertyChange(PROP_DESRIPTION);
	}
	public String getEntryAction() {
		return entryAction;
	}
	public void setEntryAction(String entryAction) {
		this.entryAction = entryAction;
		firePropertyChange(PROP_ENTRY_ACTION);
	}
	public String getExitAction() {
		return exitAction;
	}
	public void setExitAction(String existAction) {
		this.exitAction = existAction;
		firePropertyChange(PROP_EXIT_ACTION);
	}
	public List<StateTransition> getInputs() {
		return inputs;
	}
	public void setInputs(List<StateTransition> inputs) {
		this.inputs = inputs;
		firePropertyChange(PROP_INPUTS);
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
		firePropertyChange(PROP_REFERENCE);
	}
	
	public void removeOutput(StateTransition output){
		if(!outputs.contains(output))
			return;
		outputs.remove(output);
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
