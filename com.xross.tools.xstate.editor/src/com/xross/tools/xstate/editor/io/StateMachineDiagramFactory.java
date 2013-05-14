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
		
		StateMachine sm = new StateMachine();
		
		StateNode a = new StateNode();
		a.setSize(new Dimension(100, 50));
		a.setLocation(new Point(100, 200));
		StateNode b = new StateNode();
		b.setSize(new Dimension(100, 50));
		b.setLocation(new Point(300, 200));
		new StateTransition(a, b);
		smd.getMachines().add(sm);
		return smd;
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
