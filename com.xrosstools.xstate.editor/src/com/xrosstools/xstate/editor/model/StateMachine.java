package com.xrosstools.xstate.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class StateMachine implements StateMachineConstants, IPropertySource {
    public static final int DEFAULT_WIDTH = 1200;
    public static final int DEFAULT_HEIGHT = 600;
    
	private String name;
	private String description;

	private List<StateNode> nodes = new ArrayList<StateNode>();
	private List<Event> events = new ArrayList<Event>();
	private int height = DEFAULT_HEIGHT;
	private int width = DEFAULT_WIDTH;
	
	private StateMachineHelper helper = new StateMachineHelper(this);
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	protected void firePropertyChange(String propertyName){
		listeners.firePropertyChange(propertyName, null, null);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(PROP_ID, PROP_ID),
				new TextPropertyDescriptor(PROP_DESRIPTION, PROP_DESRIPTION),
				new TextPropertyDescriptor(PROP_HEIGHT, PROP_HEIGHT),
				new TextPropertyDescriptor(PROP_WIDTH, PROP_WIDTH),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_ID.equals(propName))
			return getValue(name);
		if (PROP_DESRIPTION.equals(propName))
			return getValue(description);
		if(PROP_HEIGHT.equals(propName))
		    return String.valueOf(height);
        if(PROP_WIDTH.equals(propName))
            return String.valueOf(width);
		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_ID.equals(propName))
			setName((String)value);
		if (PROP_DESRIPTION.equals(propName))
			setDescription((String)value);
        if(PROP_HEIGHT.equals(propName))
            setHeight(Integer.parseInt((String)value));
        if(PROP_WIDTH.equals(propName))
            setWidth(Integer.parseInt((String)value));
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

	public void removeNode(StateNode node){
		nodes.remove(node);
		firePropertyChange(STATE_NODE);
	}

	public void addNode(StateNode node){
		nodes.add(node);
		firePropertyChange(STATE_NODE);
	}

	public void removeEvent(Event event){
		events.remove(event);
		firePropertyChange(EVENT);
	}

	public void addEvent(Event event){
		events.add(event);
		firePropertyChange(EVENT);
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

	public List<StateNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<StateNode> nodes) {
		this.nodes = nodes;
		firePropertyChange(STATE_NODE);
	}
	
	public List<Event> getEvents() {
		return events;
	}
	
	public StateMachineHelper getHelper() {
		return helper;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
		firePropertyChange(EVENT);
	}

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        firePropertyChange(PROP_HEIGHT);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        firePropertyChange(PROP_WIDTH);
    }	
}
