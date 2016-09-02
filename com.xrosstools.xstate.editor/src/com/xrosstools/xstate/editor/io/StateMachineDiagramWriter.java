package com.xrosstools.xstate.editor.io;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.xross.tools.xstate.StateMachineDiagramConstants;
import com.xrosstools.xstate.editor.model.EndNode;
import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.StartNode;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;

public class StateMachineDiagramWriter implements StateMachineDiagramConstants {
	public Document writeToDocument(StateMachineDiagram model){
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root = (Element)doc.createElement(STATE_MACHINE_DIAGRAM);
			doc.appendChild(root);

			createNameDesc(doc, root, model.getName(), model.getDescription());			
			
			Element machinesNode = createNode(doc, root, STATE_MACHINES);
			writeMachines(doc, machinesNode, model);

			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void writeMachines(Document doc, Element machinesNode, StateMachineDiagram model) {
		for(StateMachine machine: model.getMachines()) {
			Element machineNode = createNode(doc, machinesNode, STATE_MACHINE);
			writeMachine(doc, machineNode, machine);
		}
	}
	
	private void writeMachine(Document doc, Element machineNode, StateMachine machine) {
		createNameDesc(doc, machineNode, machine.getName(), machine.getDescription());
		
		Element statesNode = createNode(doc, machineNode, STATES);
		Element eventsNode = createNode(doc, machineNode, EVENTS);
		Element transitionsNode = createNode(doc, machineNode, TRANSITIONS);
		machineNode.appendChild(statesNode);
		machineNode.appendChild(eventsNode);
		machineNode.appendChild(transitionsNode);

		writeStatesAndTransitions(doc, statesNode, machine.getNodes(), transitionsNode);
		writeEvents(doc, eventsNode, machine.getEvents());
	}
	
	private void writeStatesAndTransitions(Document doc, Element statesNode, List<StateNode> nodes, Element transitionsNode) {
		for(StateNode node: nodes) {
			Element stateNode = (Element)doc.createElement(getNodeType(node));
			statesNode.appendChild(stateNode);
			writeState(doc, stateNode, node);
			writeTransitions(doc, transitionsNode, node.getOutputs());
		}
	}

	private void writeEvents(Document doc, Element eventsNode, List<Event> events) {
		for(Event event: events) {
			createTextNode(doc, eventsNode, EVENT, event.getDescription()).
				setAttribute(ID,  event.getId());
		}
	}
	
	private String getNodeType(StateNode node) {
		if(node instanceof StartNode)
			return START_STATE;
		else
		if(node instanceof EndNode)
			return END_STATE;
		else
			return STATE;
	}
	
	private void writeState(Document doc, Element stateNode, StateNode node) {
		createIdDesc(doc, stateNode, node.getId(), node.getDescription());
		
		createTextNode(doc, stateNode, REFERENCE, node.getReference());
		createTextNode(doc, stateNode, ENTRY_ACTION, node.getEntryAction());
		createTextNode(doc, stateNode, EXIT_ACTION, node.getExitAction());

		stateNode.setAttribute(X_LOC, String.valueOf(node.getLocation().x));
		stateNode.setAttribute(Y_LOC, String.valueOf(node.getLocation().y));
	}
	
	private void writeTransitions(Document doc, Element transitionsNode, List<StateTransition> outputs) {
		for(StateTransition transition: outputs) {
			Element node = createNode(doc, transitionsNode, TRANSITION);
			if(transition.getEvent() != null)
				node.setAttribute(EVENT_ID, transition.getEvent().getId());
			node.setAttribute(SOURCE_ID, transition.getSource().getId());
			node.setAttribute(TARGET_ID, transition.getTarget().getId());
			node.setAttribute(TRANSIT_ACTION, transition.getTransitAction());
		}
	}
	
	private void createNameDesc(Document doc, Element node, String name, String desc) {
		createTextNode(doc, node, NAME, name);
		createTextNode(doc, node, DESCRIPTION, desc);
	}
	
	private void createIdDesc(Document doc, Element node, String id, String desc) {
		node.setAttribute(ID, id);
		createTextNode(doc, node, DESCRIPTION, desc);
	}
	
	private Element createNode(Document doc, Element parent, String name) {
		Element node = (Element)doc.createElement(name);
		parent.appendChild(node);
		return node;
	}
	
	private Element createTextNode(Document doc, Element node, String name, String value) {
		Element textNode = (Element)doc.createElement(name);
		node.appendChild(textNode);
		if(value == null)
			return textNode;
		
		textNode.appendChild(doc.createTextNode(value));
		return textNode;
	}
}
