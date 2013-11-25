package com.xross.tools.xstate.editor.model;

import java.beans.PropertyChangeSupport;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class Event implements StateMachineConstants, IPropertySource {
	private String id;
	private String description;

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
				new TextPropertyDescriptor(PROP_ID, PROP_ID),
				new TextPropertyDescriptor(PROP_DESRIPTION, PROP_DESRIPTION),

			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_ID.equals(propName))
			return getValue(id);
		if (PROP_DESRIPTION.equals(propName))
			return getValue(description);

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_ID.equals(propName))
			setId((String)value);
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
}
