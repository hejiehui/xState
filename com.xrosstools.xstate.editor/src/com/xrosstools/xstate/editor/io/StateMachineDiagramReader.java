package com.xrosstools.xstate.editor.io;

import static com.xrosstools.common.XmlHelper.getValidChildNodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.xrosstools.xstate.StateMachineDiagramConstants;
import com.xrosstools.xstate.editor.model.EndNode;
import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.RouteStyle;
import com.xrosstools.xstate.editor.model.StartNode;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;

public class StateMachineDiagramReader implements StateMachineDiagramConstants {
	public StateMachineDiagram getFromDocument(Document doc){
		StateMachineDiagram model = new StateMachineDiagram();
		Element root = doc.getDocumentElement();

		model.setName(getChildNodeText(root, NAME));
		model.setDescription(getChildNodeText(root, DESCRIPTION));
		model.setMachines(readMachines(getChildNode(root, STATE_MACHINES)));
		
		return model;
	}
	
	private List<StateMachine> readMachines(Node machinesNode) {
		List<Node> machines = getValidChildNodes(machinesNode);
		List<StateMachine> machineList = new ArrayList<StateMachine>();
		for(int i = 0;i < machines.size(); i++) {
			machineList.add(readMachine(machines.get(i)));
		}
		return machineList;
	}
	
	private StateMachine readMachine(Node machineNode) {
		StateMachine machine = new StateMachine();
		machine.setName(getChildNodeText(machineNode, NAME));
		machine.setDescription(getChildNodeText(machineNode, DESCRIPTION));

		Node statesNode = getChildNode(machineNode, STATES);
		Node eventsNode = getChildNode(machineNode, EVENTS);
		Node transitionsNode = getChildNode(machineNode, TRANSITIONS);

		machine.setEvents(readEvents(eventsNode));
		machine.setNodes(readStates(statesNode));
		linkState(machine, transitionsNode);
		
		return machine;
	}
	
	private List<StateNode> readStates(Node statesNode) {
		List<Node> states = getValidChildNodes(statesNode);
		List<StateNode> nodes = new ArrayList<StateNode>();
		for(int i = 0; i < states.size(); i++) {
			nodes.add(readState(states.get(i)));
		}
		return nodes;
	}
	
	private StateNode readState(Node stateNode) {
		StateNode node = createStateNode(stateNode);
		node.setId(getAttribute(stateNode, ID));
		node.setDescription(getChildNodeText(stateNode, DESCRIPTION));
		
		node.setReference(getChildNodeText(stateNode, REFERENCE));
		node.setEntryAction(getChildNodeText(stateNode, ENTRY_ACTION));
		node.setExitAction(getChildNodeText(stateNode, EXIT_ACTION));

		node.setLocation(new Point(
				getIntAttribute(stateNode, X_LOC), 
				getIntAttribute(stateNode, Y_LOC)));
		
		return node;
	}
	
	private StateNode createStateNode(Node stateNode) {
		if(stateNode.getNodeName().equals(START_STATE))
			return new StartNode();
		else
			if(stateNode.getNodeName().equals(END_STATE))
			return new EndNode();
		else
			return new StateNode();

	}
	private void linkState(StateMachine machine, Node transitionsNode) {
		List<Node> transitions = getValidChildNodes(transitionsNode);
		Map<String, StateNode> states = new HashMap<String, StateNode>();
		Map<String, Event> events = new HashMap<String, Event>();
		
		for(StateNode node: machine.getNodes()) {
			states.put(node.getId(), node);
		}
		
		for(Event event: machine.getEvents()) {
			events.put(event.getId(), event);
		}

		for(int i = 0; i < transitions.size(); i++) {
			Node node = transitions.get(i);
			StateNode source = states.get(getAttribute(node, SOURCE_ID));
			StateNode target = states.get(getAttribute(node, TARGET_ID));
			Event event = events.get(getAttribute(node, EVENT_ID));
			
			RouteStyle style = getAttribute(node, STYLE) == null ? 
			        RouteStyle.direct :
			            RouteStyle.valueOf(getAttribute(node, STYLE));
			
			StateTransition transition = new StateTransition(source, target, style, machine.getHelper());
			transition.setEvent(event);
			transition.setTransitAction(getAttribute(node, TRANSIT_ACTION));
		}
	}

	private List<Event> readEvents(Node eventsNode) {
		List<Node> events = getValidChildNodes(eventsNode);
		List<Event> eventList = new ArrayList<Event>();
		for(int i = 0; i < events.size(); i++) {
			Node eventNode = events.get(i);
			Event event = new Event();
			event.setId(getAttribute(eventNode, ID));
			event.setDescription(eventNode.getTextContent());
			eventList.add(event);
		}
		return eventList;
	}
	
	private String getChildNodeText(Node node, String childName) {
		Node child = getChildNode(node, childName);
		if(child == null)
			return null;

		return child.getTextContent();
	}
	
	private Node getChildNode(Node node, String name) {
		List<Node> children = getValidChildNodes(node);
		Node found = null;
		for(int i = 0; i < children.size(); i++){
			if(!children.get(i).getNodeName().equalsIgnoreCase(name))
				continue;
			found = children.get(i);
			break;
		}
		return found;
	}
	
	private String getAttribute(Node node, String attributeName){
		NamedNodeMap map = node.getAttributes();
		for(int i = 0; i < map.getLength(); i++)
			if(attributeName.equals(map.item(i).getNodeName()))
				return map.item(i).getNodeValue();

		return null;
	}
	
	private int getIntAttribute(Node node, String attributeName) {
		return Integer.parseInt(getAttribute(node, attributeName));
	}
}
