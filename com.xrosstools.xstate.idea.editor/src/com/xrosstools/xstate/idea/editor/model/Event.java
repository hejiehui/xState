package com.xrosstools.xstate.idea.editor.model;

import java.beans.PropertyChangeSupport;

import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;

public class Event implements StateMachineConstants, IPropertySource {
	private String id;
	private String label;
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
				new TextPropertyDescriptor(PROP_LABEL, PROP_LABEL),
				new TextPropertyDescriptor(PROP_DESCRIPTION, PROP_DESCRIPTION),

			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_ID.equals(propName))
			return getValue(id);
		if (PROP_LABEL.equals(propName))
			return getValue(label);
		if (PROP_DESCRIPTION.equals(propName))
			return getValue(description);

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_ID.equals(propName))
			setId((String)value);
		if (PROP_LABEL.equals(propName))
			setLabel((String)value);
		if (PROP_DESCRIPTION.equals(propName))
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
		firePropertyChange(PROP_DESCRIPTION);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label == null ? null : label.trim();
		firePropertyChange(PROP_LABEL);
	}

	public String getDisplayText(){
		return label == null || label.length() == 0 ? (id == null ? "" : id) : label;
	}
}
