package com.xross.tools.xstate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.xross.tools.xstate.def.ActionDef;
import com.xross.tools.xstate.def.EventDef;
import com.xross.tools.xstate.def.StateDef;
import com.xross.tools.xstate.def.StateMachineDef;
import com.xross.tools.xstate.def.TransitionDef;

/**
 * TODO revise factory
 * @author jhhe
 *
 */
public class StateMachineDiagramFactory implements StateMachineDiagramConstants {
	private static StateMachineDiagramFactory factory = new StateMachineDiagramFactory();
	
	public static StateMachineDiagram load(URL url) throws SAXException, IOException, ParserConfigurationException {
		return load(url.openStream());
	}
	
	public static StateMachineDiagram load(String path) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		return load(new File(path));
	}
	
	public static StateMachineDiagram load(File model) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		return load(new FileInputStream(model));
	}
	
	public static StateMachineDiagram load(InputStream in) throws SAXException, IOException, ParserConfigurationException {
		try{
			Document doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
			StateMachineDiagram def = factory.getFromDocument(doc);
			in.close();
			return def;
		}catch(Throwable e){
			if(in != null)
				try{
					in.close();
				}catch(Throwable e1){
					
				}
			e.printStackTrace();
		}

		return null;
	}
	
	public StateMachineDiagram getFromDocument(Document doc){
		StateMachineDiagram model = new StateMachineDiagram();
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
	private void linkState(StateMachineDef machineDef, Node transitionsNode) {
		NodeList transitions = transitionsNode.getChildNodes();
		Map<String, StateDef> states = new HashMap<String, StateDef>();
		Map<String, EventDef> events = new HashMap<String, EventDef>();
		
		for(StateDef node: machineDef.getStates()) {
			states.put(node.getId(), node);
		}
		
		for(EventDef event: machineDef.getEvents()) {
			events.put(event.getId(), event);
		}

		for(int i = 0; i < transitions.getLength(); i++) {
			Node node = transitions.item(i);
			StateDef source = states.get(getAttribute(node, SOURCE_ID));
			StateDef target = states.get(getAttribute(node, TARGET_ID));
			EventDef event = events.get(getAttribute(node, EVENT_ID));
			TransitionDef transition = new TransitionDef(source, target);
			transition.setEventDef(event);
			transition.setTransitActionDef(new ActionDef(getAttribute(node, TRANSIT_ACTION)));
		}
	}

	private List<EventDef> readEvents(Node eventsNode) {
		NodeList events = eventsNode.getChildNodes();
		List<EventDef> eventList = new ArrayList<EventDef>();
		for(int i = 0; i < events.getLength(); i++) {
			Node eventNode = events.item(i);
			EventDef event = new EventDef();
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
}