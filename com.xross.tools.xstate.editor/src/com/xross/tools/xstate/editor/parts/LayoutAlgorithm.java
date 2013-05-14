package com.xross.tools.xstate.editor.parts;

import com.xross.tools.xstate.editor.model.StateMachine;

public class LayoutAlgorithm {

	public void layout(StateMachine stateMachine){
//		findRoots(diagram);
//		int nextTreePos = 0;
//		for(DecisionTreeRoot root : diagram.getRoots()){
//			buildLines(root);
//			layout(diagram, root, nextTreePos);
//			nextTreePos += root.getWidth();
//		}
//	}
//	
//	private void findRoots(DecisionTreeDiagram diagram){
//		List<DecisionTreeRoot> newRoots = new ArrayList<DecisionTreeRoot>();
//		Set<DecisionTreeNode> proceed = new HashSet<DecisionTreeNode>();
//		
//		// First keep old root at list start
//		for(DecisionTreeRoot root : diagram.getRoots()){
//			DecisionTreeNode node = root.getRootNode();
//			if(node.getInput() == null && diagram.getNodes().contains(node)){
//				newRoots.add(root);
//				proceed.add(node);
//			}
//		}
//		
//		for(DecisionTreeNode node: diagram.getNodes()){
//			if(proceed.contains(node))
//				continue;
//			if(node.getInput() == null)
//				newRoots.add(new DecisionTreeRoot(node));
//		}
//		
//		diagram.setRoots(newRoots);
//	}
//	
//	private void layout(DecisionTreeDiagram diagram, DecisionTreeRoot root, int nextTreePos){
//    	Dimension size = new Dimension(diagram.getNodeWidth(), diagram.getNodeHeight());
//    	
//    	int margin  = 100;
//    	float leftSpace = -margin + (diagram.getAlignment() + 0) * (diagram.getHorizantalSpace() + size.width);
//    	
//    	leftSpace -= (diagram.getHorizantalSpace() + size.width) * nextTreePos;
//    	
//    	for(int rowNum = 0; rowNum < root.getRows().size(); rowNum++){
//    		DecisionTreeRow row = root.getRows().get(rowNum);
//    		int width = root.getWidth()/row.getRowNodes().size();
//    		for(int colNum = 0; colNum < row.getRowNodes().size(); colNum++){
//    			DecisionTreeNode node = row.getRowNodes().get(colNum);
//    			node.setSize(size);
//    			
//    			float x = -leftSpace + (colNum + diagram.getAlignment())* width * (diagram.getHorizantalSpace() + size.width);
//    			int y = margin + (rowNum) * (diagram.getVerticalSpace() + size.height);
//    			
//        		node.setLocation(diagram.isHorizantal()? new Point(y, x): new Point(x, y));
//    		}
//    	}
//	}
//	
//	private void buildLines(DecisionTreeRoot root){
//		root.getRows().clear();
//		visit(root.getRows(), root.getRootNode(), 0);
//		
//		int width = 1;
//		for(DecisionTreeRow row : root.getRows()){
//			//for the last row, the max width is always 0
//			if(row.getMaxChidrenNumber() != 0)
//				width *= row.getMaxChidrenNumber();
//		}
//		
//		root.setWidth(width);
//	}
//	
//	private void visit(List<DecisionTreeRow> rows, DecisionTreeNode curNode, int depth){
//		if(rows.size() == depth)
//			rows.add(new DecisionTreeRow());
//		
//		DecisionTreeRow row = rows.get(depth);
//		row.getRowNodes().add(curNode);
//		
//		if(row.getMaxChidrenNumber() < curNode.getOutputs().size())
//			row.setMaxChidrenNumber(curNode.getOutputs().size());
//		
//		for(DecisionTreeNodeConnection path : curNode.getOutputs()){
//			visit(rows, path.getChild(), depth + 1);			
//		}
	}
}
