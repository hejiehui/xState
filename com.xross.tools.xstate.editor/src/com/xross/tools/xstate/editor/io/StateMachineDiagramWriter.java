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

public class StateMachineDiagramWriter implements StateMachineDiagramConstants {
	public Document writeToDocument(StateMachineDiagram model){
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root = (Element)doc.createElement(STATE_MACHINE_DIAGRAM);
			doc.appendChild(root);

			createNameDesc(doc, root, model.getName(), model.getDescription());			
			
			Element machinesNode = (Element)doc.createElement(STATE_MACHINES);
			root.appendChild(machinesNode);
			writeMachines(doc, machinesNode, model);

			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private void writeMachines(Document doc, Element machinesNode, StateMachineDiagram model) {
		for(StateMachine machine: model.getMachines()) {
			Element machineNode = (Element)doc.createElement(STATE_MACHINE);
			machinesNode.appendChild(machineNode);
			writeMachine(doc, machineNode, machine);
		}
	}
	
	private void writeMachine(Document doc, Element machineNode, StateMachine machine) {
		createNameDesc(doc, machineNode, machine.getName(), machine.getDescription());
		
		Element statesNode = (Element)doc.createElement(STATES);
		machineNode.appendChild(statesNode);
		
		Element eventsNode = (Element)doc.createElement(EVENTS);
		machineNode.appendChild(eventsNode);
		
		Element transitionsNode = (Element)doc.createElement(TRANSITIONS);
		machineNode.appendChild(transitionsNode);

		writeStatesAndTransitions(doc, statesNode, machine.getNodes(), transitionsNode);
		writeEvents(doc, eventsNode, machine.getEvents());
	}
	
	private void writeStatesAndTransitions(Document doc, Element statesNode, List<StateNode> nodes, Element transitionsNode) {
		for(StateNode node: nodes) {
			Element stateNode = (Element)doc.createElement(STATE);
			statesNode.appendChild(stateNode);
			writeState(doc, stateNode, node);
		}
	}

	private void writeEvents(Document doc, Element eventsNode, List<Event> events) {
		
	}
	
	private void writeState(Document doc, Element stateNode, StateNode node) {
		createNameDesc(doc, stateNode, node.getName(), node.getDescription());
		createTextNode(doc, stateNode, ENTER_ACTION, node.getEnterAction());
		createTextNode(doc, stateNode, EXIT_ACTION, node.getExitAction());
		createTextNode(doc, stateNode, EXIT_ACTION, node.getExitAction());
	}
	
	private void createNameDesc(Document doc, Element node, String name, String desc) {
		createTextNode(doc, node, NAME, name);
		createTextNode(doc, node, DESCRIPTION, desc);
	}
	
	private void createTextNode(Document doc, Element node, String name, String value) {
		Element textNode = (Element)doc.createElement(name);
		if(value == null)
			return;
		
		textNode.appendChild(doc.createTextNode(value));
		node.appendChild(textNode);
	}
}
