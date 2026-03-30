package com.xrosstools.xstate.idea.editor.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.xrosstools.idea.gef.util.ComboBoxPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;

public class StateTransition implements StateMachineConstants, IPropertySource, PropertyChangeListener {
	private Event event;
    private String transitGuard;
	private String transitAction;
	private StateNode source;
	private StateNode target;
	private StateMachineHelper helper;
	private RouteStyle style;
	
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
				new TextPropertyDescriptor(PROP_TRANSITION_GUARD, PROP_TRANSITION_GUARD),
				new TextPropertyDescriptor(PROP_TRANSITION_ACTION, PROP_TRANSITION_ACTION),
				new ComboBoxPropertyDescriptor(PROP_STYLE, PROP_STYLE, RouteStyle.getNames()),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_EVENT.equals(propName))
			return helper.getEventIdIndex(event);
		if (PROP_TRANSITION_ACTION.equals(propName))
			return getValue(transitAction);
		if (PROP_TRANSITION_GUARD.equals(propName))
			return getValue(transitGuard);
		if(PROP_STYLE.equals(propName))
            return RouteStyle.getIndex(style);
		
		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_EVENT.equals(propName))
			setEvent(helper.getEvent((Integer)value));
		if (PROP_TRANSITION_ACTION.equals(propName))
			setTransitAction((String)value);
		if (PROP_TRANSITION_GUARD.equals(propName))
			setTransitGuard((String)value);
		if(PROP_STYLE.equals(propName))
            setStyle(RouteStyle.values()[(Integer)value]);
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

	public StateTransition(StateNode source, StateNode target, RouteStyle style, StateMachineHelper helper){
		this.source =source;
		this.target = target;
		this.helper = helper;
		this.style = style;
		source.addOutput(this);
		target.addInput(this);
	}

	public StateMachineHelper getHelper() {
        return helper;
    }

    public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
		if(event != null)
			event.getListeners().addPropertyChangeListener(this);
		firePropertyChange(PROP_EVENT);
	}
	public String getTransitAction() {
		return transitAction;
	}
	public void setTransitAction(String transitAction) {
		this.transitAction = transitAction;
		firePropertyChange(PROP_TRANSITION_ACTION);
	}
    public String getTransitGuard() {
        return transitGuard;
    }
    public void setTransitGuard(String transitGuard) {
        this.transitGuard = transitGuard;
        firePropertyChange(PROP_TRANSITION_GUARD);
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
		if(event == null || event.getId() == null)
		    return NOT_SPECIFIED;
		return event.getDisplayText();
	}
    public RouteStyle getStyle() {
        return style;
    }
    public void setStyle(RouteStyle style) {
        this.style = style;
        firePropertyChange(PROP_STYLE);
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		listeners.firePropertyChange(PROP_EVENT, null, event);
	}
}
