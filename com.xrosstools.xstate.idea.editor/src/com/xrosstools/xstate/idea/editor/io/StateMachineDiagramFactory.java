package com.xrosstools.xstate.idea.editor.io;

import com.xrosstools.xstate.idea.editor.model.StateMachineConstants;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import org.w3c.dom.Document;


public class StateMachineDiagramFactory implements StateMachineConstants {
	public static final String LOCATION = "location";
	
	public StateMachineDiagram getEmptyDiagram(){
		StateMachineDiagram smd = new StateMachineDiagram();
		smd.setName("StateMachineDiagram");	
		return smd;
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
