package com.xross.tools.xstate.editor.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.xross.tools.xstate.editor.model.StateMachineConstants;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateDiagramReader implements StateMachineConstants{
	public StateMachineDiagram getFromDocument(Document doc){
		StateMachineDiagram model = new StateMachineDiagram();
		Element root = doc.getDocumentElement();
		
//		model.setName(root.getAttribute(NAME));
//		model.setDescription(root.getAttribute(DESCRIPTION));
//		model.setConfigure(createConfigure(doc));

//		model.getMachines().addAll(readUnits(doc));
		
		return model;
	}
}
