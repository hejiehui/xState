package com.xross.tools.xstate.editor.io;

import org.w3c.dom.Document;

import com.xross.tools.xstate.editor.model.StateMachineConstants;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateMachineDiagramFactory implements StateMachineConstants{
	public StateMachineDiagram getEmptyDiagram(){
		StateMachineDiagram sm = new StateMachineDiagram();
		
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
