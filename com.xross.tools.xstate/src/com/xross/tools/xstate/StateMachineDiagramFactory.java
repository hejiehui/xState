package com.xross.tools.xstate;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xross.tools.xstate.def.ActionDef;
import com.xross.tools.xstate.def.StateDef;
import com.xross.tools.xstate.def.StateMachineDef;
import com.xross.tools.xstate.def.StateMachineDiagramDef;
import com.xross.tools.xstate.def.StateType;

public class StateMachineDiagramFactory implements StateMachineDiagramConstants {
	public StateMachineDiagramDef getFromDocument(Document doc){
		StateMachineDiagramDef model = new StateMachineDiagramDef();
		Element root = doc.getDocumentElement();

		model.setName(getChildNodeText(root, NAME));
		model.setDescription(getChildNodeText(root, DESCRIPTION));
		model.setStateMachines(readMachines(getChildNode(root, STATE_MACHINES)));
		
		return model;
	}
	
	private Map<String, StateMachineDef> readMachines(Node machinesNode) {
		NodeList machines = machinesNode.getChildNodes();
		Map<String, StateMachineDef> machineMap = new HashMap<String, StateMachineDef>();
		for(int i = 0;i < machines.getLength(); i++) {
			StateMachineDef def = readMachine(machines.item(i));
			machineMap.put(def.getName(), def);
		}
		return machineMap;
	}
	
	private StateMachineDef readMachine(Node machineNode) {
		StateMachineDef machine = new StateMachineDef();
		machine.setName(getChildNodeText(machineNode, NAME));
		machine.setDescription(getChildNodeText(machineNode, DESCRIPTION));

		Node statesNode = getChildNode(machineNode, STATES);
		Node eventsNode = getChildNode(machineNode, EVENTS);
		Node transitionsNode = getChildNode(machineNode, TRANSITIONS);

		machine.setEvents(readEvents(eventsNode));
		machine.setStates(readStates(statesNode));
		linkState(machine, transitionsNode);
		
		return machine;
	}
	
	private List<StateDef> readStates(Node statesNode) {
		NodeList states = statesNode.getChildNodes();
		List<StateDef> nodes = new ArrayList<StateDef>();
		for(int i = 0; i < states.getLength(); i++) {
			nodes.add(readState(states.item(i)));
		}
		return nodes;
	}
	
	private StateDef readState(Node stateNode) {
		StateDef node = createStateNode(stateNode);
		node.setId(getAttribute(stateNode, ID));
		node.setDescription(getChildNodeText(stateNode, DESCRIPTION));
		
		node.setEntryActionDef(new ActionDef(getChildNodeText(stateNode, ENTRY_ACTION)));
		node.setExitActionDef(new ActionDef(getChildNodeText(stateNode, EXIT_ACTION)));

		return node;
	}
	
	private StateDef createStateNode(Node stateNode) {
		StateDef def = new StateDef();
		StateType type;
		if(stateNode.getNodeName().equals(START_STATE))
			type = StateType.start;
		else
			if(stateNode.getNodeName().equals(END_STATE))
				type = StateType.end;
		else
			type = StateType.normal;
		def.setType(type);
		return def;
	}
	private void linkState(StateMachine machine, Node transitionsNode) {
		NodeList transitions = transitionsNode.getChildNodes();
		Map<String, StateNode> states = new HashMap<String, StateNode>();
		Map<String, Event> events = new HashMap<String, Event>();
		
		for(StateNode node: machine.getNodes()) {
			states.put(node.getId(), node);
		}
		
		for(Event event: machine.getEvents()) {
			events.put(event.getId(), event);
		}

		for(int i = 0; i < transitions.getLength(); i++) {
			Node node = transitions.item(i);
			StateNode source = states.get(getAttribute(node, SOURCE_ID));
			StateNode target = states.get(getAttribute(node, TARGET_ID));
			Event event = events.get(getAttribute(node, EVENT_ID));
			StateTransition transition = new StateTransition(source, target, machine.getHelper());
			transition.setEvent(event);
			transition.setTransitAction(getAttribute(node, TRANSIT_ACTION));
		}
	}

	private List<Event> readEvents(Node eventsNode) {
		NodeList events = eventsNode.getChildNodes();
		List<Event> eventList = new ArrayList<Event>();
		for(int i = 0; i < events.getLength(); i++) {
			Node eventNode = events.item(i);
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
		NodeList children = node.getChildNodes();
		Node found = null;
		for(int i = 0; i < children.getLength(); i++){
			if(!children.item(i).getNodeName().equalsIgnoreCase(name))
				continue;
			found = children.item(i);
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