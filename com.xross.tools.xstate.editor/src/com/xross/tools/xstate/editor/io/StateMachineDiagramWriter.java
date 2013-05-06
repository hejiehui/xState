package com.xross.tools.xstate.editor.io;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.xross.tools.xstate.editor.model.StateMachineConstants;
import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateMachineDiagramWriter implements StateMachineConstants{
	public Document writeToDocument(StateMachineDiagram model){
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//			Element root = (Element)doc.createElement(XUNIT);
//			doc.appendChild(root);
//
//			appendDescription(doc, root, model);
//			appendImports(doc, root, model);
//			root.appendChild(createConfigure(doc, model.getConfigure()));
//			
//			Element unitsNode = (Element)doc.createElement(UNITS);
//			root.appendChild(unitsNode);
//			for(UnitNode unit: model.getUnits())
//				unitsNode.appendChild(createUnitNode(doc, unit));
//
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
