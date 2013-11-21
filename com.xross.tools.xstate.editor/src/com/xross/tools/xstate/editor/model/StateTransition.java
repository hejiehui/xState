package com.xross.tools.xstate.editor.model;

import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class StateTransition implements StateMachineConstants, IPropertySource {
	private Event event;
	private String transitAction;
	private StateNode source;
	private StateNode target;
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	protected void firePropertyChange(String propertyName){
		listeners.firePropertyChange(propertyName, null, null);
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(PROP_EVENT, event.getId()),
				new TextPropertyDescriptor(PROP_TRANSITION_ACTION, transitAction),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
//		if (PROP_EVENT.equals(propName))
//			return name;
		if (PROP_TRANSITION_ACTION.equals(propName))
			return transitAction;
		
		return null;
	}

	public void setPropertyValue(Object propName, Object value){
//		if (PROP_NAME.equals(propName))
//			setEvent((String)value);
		if (PROP_TRANSITION_ACTION.equals(propName))
			setTransitAction((String)value);
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}
	
	public void resetPropertyValue(Object propName){
	}

	public StateTransition(StateNode source, StateNode target){
		this.source =source;
		this.target = target;
		source.addOutput(this);
		target.addInput(this);
	}

	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public String getTransitAction() {
		return transitAction;
	}
	public void setTransitAction(String transitAction) {
		this.transitAction = transitAction;
	}
	public StateNode getSource() {
		return source;
	}
	public void setSource(StateNode source) {
		this.source = source;
	}
	public StateNode getTarget() {
		return target;
	}
	public void setTarget(StateNode target) {
		this.target = target;
	}
	
	public String getDisplayLabel(){
		StringBuffer sbf = new StringBuffer();
		if(event != null && event.getId() != null)
			sbf.append(event.getId());
		
		if(transitAction != null)
			sbf.append(transitAction);
		
		if(sbf.length() == 0)
			return NOT_SPECIFIED;
		return sbf.toString();
	}
	
}
