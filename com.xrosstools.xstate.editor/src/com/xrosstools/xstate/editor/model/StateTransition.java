package com.xrosstools.xstate.editor.model;

import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class StateTransition implements StateMachineConstants, IPropertySource {
	private Event event;
	private String transitAction;
	private StateNode source;
	private StateNode target;
	private StateMachineHelper helper;
	
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
				new ComboBoxPropertyDescriptor(PROP_EVENT, PROP_EVENT, helper.getEventIds()),
				new TextPropertyDescriptor(PROP_TRANSITION_ACTION, PROP_TRANSITION_ACTION),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_EVENT.equals(propName))
			return helper.getEventIdIndex(event);
		if (PROP_TRANSITION_ACTION.equals(propName))
			return getValue(transitAction);
		
		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_EVENT.equals(propName))
			setEvent(helper.getEvent((Integer)value));
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

	private String getValue(String value) {
		return value == null? "" : value;
	}

	public StateTransition(StateNode source, StateNode target, StateMachineHelper helper){
		this.source =source;
		this.target = target;
		this.helper = helper;
		source.addOutput(this);
		target.addInput(this);
	}

	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
		firePropertyChange(PROP_EVENT);
	}
	public String getTransitAction() {
		return transitAction;
	}
	public void setTransitAction(String transitAction) {
		this.transitAction = transitAction;
		firePropertyChange(PROP_TRANSITION_ACTION);
	}
	public StateNode getSource() {
		return source;
	}
	public void setSource(StateNode source) {
		this.source = source;
		firePropertyChange(PROP_SOURCE);
	}
	public StateNode getTarget() {
		return target;
	}
	public void setTarget(StateNode target) {
		this.target = target;
		firePropertyChange(PROP_TARGET);
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
