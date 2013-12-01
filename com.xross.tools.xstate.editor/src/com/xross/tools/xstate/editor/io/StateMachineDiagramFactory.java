package com.xross.tools.xstate.editor.io;

import org.eclipse.draw2d.geometry.Point;
import org.w3c.dom.Document;

import com.xross.tools.xstate.editor.model.Event;
import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineConstants;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.model.StateTransition;

public class StateMachineDiagramFactory implements StateMachineConstants{
	public static final String LOCATION = "location";
	
	public StateMachineDiagram getEmptyDiagram(){
		StateMachineDiagram smd = new StateMachineDiagram();
		smd.setName("StateMachineDiagram");	
		
//		smd.getMachines().add(createStateMachine("state machine 1", 0));
		smd.getMachines().add(createStateMachine("state machine 2", 1));
//		smd.getMachines().add(createStateMachine("state machine 3", 2));
		
		return smd;
	}
	
	private StateMachine createStateMachine(String name, int num){
		StateMachine sm = new StateMachine();
		sm.setName(name);
		
		StateNode a = new StateNode();
		a.setLocation(new Point(0, 0));
		a.setId("start");
		sm.getNodes().add(a);

		for(int i = 0; i < num; i++)
		{
			StateNode b = new StateNode();
			b.setLocation(new Point((i+1)* 200, 0));
			b.setId("state" + i);
			sm.getNodes().add(b);
			
			StateTransition t = new StateTransition(a, b, sm.getHelper());
			Event evt = new Event();
			evt.setId("event " + i);
			t.setEvent(evt);
			
			a = b;
		}		
		
		return sm;
	}
	
	private StateMachineDiagramReader reader = new StateMachineDiagramReader();
	private StateMachineDiagramWriter writer = new StateMachineDiagramWriter();
	public StateMachineDiagram getFromDocument(Document doc){
		return reader.getFromDocument(doc);
	}
	
	public Document writeToDocument(StateMachineDiagram model){
		return writer.writeToDocument(model);
	}
}
