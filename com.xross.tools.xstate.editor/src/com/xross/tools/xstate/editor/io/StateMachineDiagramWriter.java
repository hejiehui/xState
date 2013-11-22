package com.xross.tools.xstate.editor.io;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.xross.tools.xstate.StateMachineDiagramConstants;
import com.xross.tools.xstate.editor.model.Event;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.model.StateTransition;

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
			Element stateNode = (Element)doc.createElement(STATE);
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
	
	private void writeState(Document doc, Element stateNode, StateNode node) {
		createIdDesc(doc, stateNode, node.getId(), node.getDescription());
		createTextNode(doc, stateNode, ENTER_ACTION, node.getEnterAction());
		createTextNode(doc, stateNode, EXIT_ACTION, node.getExitAction());

		Element location = createNode(doc, stateNode, StateMachineDiagramFactory.LOCATION);
		location.setAttribute(StateMachineDiagramFactory.X, String.valueOf(node.getLocation().x));
		location.setAttribute(StateMachineDiagramFactory.Y, String.valueOf(node.getLocation().y));

		Element size = createNode(doc, stateNode, StateMachineDiagramFactory.SIZE);
		size.setAttribute(StateMachineDiagramFactory.HEIGHT, String.valueOf(node.getSize().height));
		size.setAttribute(StateMachineDiagramFactory.WIDTH, String.valueOf(node.getSize().width));
	}
	
	private void writeTransitions(Document doc, Element transitionsNode, List<StateTransition> outputs) {
		for(StateTransition transition: outputs) {
			Element node = createNode(doc, transitionsNode, TRANSITION);
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
		if(value == null)
			return textNode;
		
		textNode.appendChild(doc.createTextNode(value));
		node.appendChild(textNode);
		return textNode;
	}
}
