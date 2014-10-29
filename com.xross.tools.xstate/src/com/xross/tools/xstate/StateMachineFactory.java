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
import java.util.NoSuchElementException;
import java.util.Set;

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
 * @author Jerry He
 *
 */
public class StateMachineFactory implements StateMachineDiagramConstants {
	private String name;
	private String description;
	private Map<String, StateMachineDef> stateMachines;
	
	private StateMachineFactory(
			String name,
			String description,
			Map<String, StateMachineDef> stateMachines) {
		this.name = name;
		this.description = description;
		this.stateMachines = stateMachines;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Set<String> getStateMachineNames() {
		return stateMachines.keySet();
	}

	public String getStateMachineDescription(String name) {
		return stateMachines.get(name).getDescription();
	}
	
	public StateMachine create(String stateMachineName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		StateMachineDef def = stateMachines.get(stateMachineName);
		if(def == null)
			throw new NoSuchElementException(String.format("Can not found state machine definition for name: %s", stateMachineName));
		
		return def.create();
	}
	
	public static StateMachineFactory load(URL url) throws SAXException, IOException, ParserConfigurationException {
		return load(url.openStream());
	}
	
	public static StateMachineFactory load(String path) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		return load(new File(path));
	}
	
	public static StateMachineFactory load(File model) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		return load(new FileInputStream(model));
	}
	
	public static StateMachineFactory load(InputStream in) throws SAXException, IOException, ParserConfigurationException {
		try{
			Document doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
			StateMachineFactory def = getFromDocument(doc);
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
	
	private static StateMachineFactory getFromDocument(Document doc){
		Element root = doc.getDocumentElement();

		String name = getChildNodeText(root, NAME);
		String description = getChildNodeText(root, DESCRIPTION);
		
		return new StateMachineFactory(name, description, readMachines(getChildNode(root, STATE_MACHINES)));
	}
	
	private static Map<String, StateMachineDef> readMachines(Node machinesNode) {
		NodeList machines = machinesNode.getChildNodes();
		Map<String, StateMachineDef> machineMap = new HashMap<String, StateMachineDef>();
		for(int i = 0;i < machines.getLength(); i++) {
			StateMachineDef def = readMachine(machines.item(i));
			machineMap.put(def.getName(), def);
		}
		return machineMap;
	}
	
	private static StateMachineDef readMachine(Node machineNode) {
		StateMachineDef machine = new StateMachineDef();
		machine.setName(getChildNodeText(machineNode, NAME));
		machine.setDescription(getChildNodeText(machineNode, DESCRIPTION));

		Node statesNode = getChildNode(machineNode, STATES);
		Node eventsNode = getChildNode(machineNode, EVENTS);
		Node transitionsNode = getChildNode(machineNode, TRANSITIONS);

		machine.setEventDefs(readEvents(eventsNode));
		machine.setStateDefs(readStates(statesNode));
		machine.setTansitionDefs(linkState(machine, transitionsNode));
		
		return machine;
	}
	
	private static List<StateDef> readStates(Node statesNode) {
		NodeList states = statesNode.getChildNodes();
		List<StateDef> nodes = new ArrayList<StateDef>();
		for(int i = 0; i < states.getLength(); i++) {
			nodes.add(readState(states.item(i)));
		}
		return nodes;
	}
	
	private static StateDef readState(Node stateNode) {
		StateDef node = createStateNode(stateNode);
		node.setId(getAttribute(stateNode, ID));
		node.setDescription(getChildNodeText(stateNode, DESCRIPTION));
		
		node.setEntryActionDef(new ActionDef(getChildNodeText(stateNode, ENTRY_ACTION)));
		node.setExitActionDef(new ActionDef(getChildNodeText(stateNode, EXIT_ACTION)));

		return node;
	}
	
	private static StateDef createStateNode(Node stateNode) {
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
	private static List<TransitionDef> linkState(StateMachineDef machineDef, Node transitionsNode) {
		NodeList transitions = transitionsNode.getChildNodes();
		Map<String, EventDef> events = new HashMap<String, EventDef>();
		
		for(EventDef event: machineDef.getEventDefs()) {
			events.put(event.getId(), event);
		}

		List<TransitionDef> transitionDefs = new ArrayList<TransitionDef>();
		for(int i = 0; i < transitions.getLength(); i++) {
			Node node = transitions.item(i);
			String sourceId = getAttribute(node, SOURCE_ID);
			String targetId = getAttribute(node, TARGET_ID);
			EventDef event = events.get(getAttribute(node, EVENT_ID));
			TransitionDef transition = new TransitionDef(sourceId, targetId);
			transition.setEventDef(event);
			transition.setTransitActionDef(new ActionDef(getAttribute(node, TRANSIT_ACTION)));
			transitionDefs.add(transition);
		}
		
		return transitionDefs;
	}

	private static List<EventDef> readEvents(Node eventsNode) {
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
	
	private static String getChildNodeText(Node node, String childName) {
		Node child = getChildNode(node, childName);
		if(child == null)
			return null;

		return child.getTextContent();
	}
	
	private static Node getChildNode(Node node, String name) {
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
	
	private static String getAttribute(Node node, String attributeName){
		NamedNodeMap map = node.getAttributes();
		for(int i = 0; i < map.getLength(); i++)
			if(attributeName.equals(map.item(i).getNodeName()))
				return map.item(i).getNodeValue();

		return null;
	}
}