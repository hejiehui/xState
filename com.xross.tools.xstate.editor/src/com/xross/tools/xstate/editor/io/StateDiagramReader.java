package com.xross.tools.xstate.editor.io;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.xross.tools.xstate.editor.model.StateMachineConstants;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateDiagramReader implements StateMachineConstants{
	public StateMachineDiagram getFromDocument(Document doc){
		StateMachineDiagram model = new StateMachineDiagram();
		Element root = doc.getDocumentElement();
//		
//		model.setPackageId(root.getAttribute(PACKAGE_ID));
//		model.setName(root.getAttribute(NAME));
//		model.setDescription(root.getAttribute(DESCRIPTION));
//		model.setConfigure(createConfigure(doc));
//
//		model.getUnits().addAll(readUnits(doc));
		
		return model;
	}
}
