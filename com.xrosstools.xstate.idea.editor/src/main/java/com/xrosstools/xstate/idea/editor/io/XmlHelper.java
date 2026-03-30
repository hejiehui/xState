package com.xrosstools.xstate.idea.editor.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlHelper {
	public static boolean isValidNode(Node node) {
		return !node.getNodeName().equals("#text");
	}
	
	public static boolean isValidNode(Node node, String name) {
		return node.getNodeName().equals(name);
	}
	
	public static Node getFirstValidNode(Node node) {
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			if(isValidNode(children.item(i)))
				return children.item(i);
		}
		
		return null;
	}
	
	public static List<Node> getValidChildNodes(Node node) {
		List<Node> nl = new ArrayList();
		if(node == null)
		    return nl;
		
		NodeList nodeList = node.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++){
			if(isValidNode(nodeList.item(i)))
				nl.add(nodeList.item(i));
		}
		return nl;
	}
	
	public static String format(Document doc) throws Exception {
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	TransformerFactory tFactory =TransformerFactory.newInstance();
    	Transformer transformer = tFactory.newTransformer();
    	DOMSource source = new DOMSource(doc);
    	StreamResult result = new StreamResult(out);
    	transformer.transform(source, result);
    	
    	// To make well formated document
    	SAXReader reader = new SAXReader();
    	org.dom4j.Document document = reader.read(new ByteArrayInputStream(out.toByteArray()));
    	
    	XMLWriter writer = null;
        StringWriter stringWriter = new StringWriter();  
        OutputFormat format = new OutputFormat(" ", true);  
        writer = new XMLWriter(stringWriter, format);  
        writer.write(document);  
        writer.flush();  
        return stringWriter.toString();
    }

}
