package com.xross.tools.xstate.editor.io;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.w3c.dom.Document;

import com.xross.tools.xstate.editor.model.StateMachine;
import com.xross.tools.xstate.editor.model.StateMachineConstants;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;
import com.xross.tools.xstate.editor.model.StateNode;
import com.xross.tools.xstate.editor.model.StateTransition;

public class StateMachineDiagramFactory implements StateMachineConstants{
	public StateMachineDiagram getEmptyDiagram(){
		StateMachineDiagram smd = new StateMachineDiagram();
		smd.setName("StateMachineDiagram");	
		
		smd.getMachines().add(createStateMachine("state machine 1", 1));
		smd.getMachines().add(createStateMachine("state machine 2", 2));
		smd.getMachines().add(createStateMachine("state machine 3", 3));
		
		return smd;
	}
	
	private StateMachine createStateMachine(String name, int num){
		StateMachine sm = new StateMachine();
		sm.setName(name);
		
		StateNode a = new StateNode();
		a.setSize(new Dimension(100, 50));
		a.setLocation(new Point(100, 200));
		a.setName("start");
		sm.getNodes().add(a);

		for(int i = 0; i < num; i++)
		{
			StateNode b = new StateNode();
			b.setSize(new Dimension(100, 50));
			b.setLocation(new Point(100 + (i+1)* 200, 200));
			b.setName("state" + i);
			sm.getNodes().add(b);
			
			new StateTransition(a, b);
			
			a = b;
		}		
		
		return sm;
	}
	
	private StateDiagramReader reader = new StateDiagramReader();
	private StateMachineDiagramWriter writer = new StateMachineDiagramWriter();
	public StateMachineDiagram getFromDocument(Document doc){
		return reader.getFromDocument(doc);
	}
	
	public Document writeToDocument(StateMachineDiagram model){
		return writer.writeToDocument(model);
	}
}
