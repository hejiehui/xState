package com.xross.tools.xstate.editor.requests;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateNodeResizeRequest  extends Request {
	private StateMachineDiagram diagram;
	private boolean horizantal;
	private boolean nodeSize;
	private boolean increase;
	
	public StateNodeResizeRequest(StateMachineDiagram diagram, boolean nodeSize, boolean horizantal, boolean increase){
		super(RequestConstants.REQ_RESIZE);
		this.diagram = diagram;
		this.horizantal = horizantal;
		this.nodeSize = nodeSize;
		this.increase = increase;
	}

	public StateMachineDiagram getDiagram() {
		return diagram;
	}

	public boolean isHorizantal() {
		return horizantal;
	}

	public boolean isIncrease() {
		return increase;
	}

	public boolean isNodeSize() {
		return nodeSize;
	}
}
