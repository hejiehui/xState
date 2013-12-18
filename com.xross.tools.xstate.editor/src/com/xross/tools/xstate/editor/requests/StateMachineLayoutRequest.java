package com.xross.tools.xstate.editor.requests;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

import com.xross.tools.xstate.editor.model.StateMachineDiagram;

public class StateMachineLayoutRequest extends Request {
	private StateMachineDiagram diagram;
	private boolean isHorizantal;
	private float alignment;
	
	public StateMachineLayoutRequest(StateMachineDiagram diagram, boolean isHorizantal, float alignment){
		super(RequestConstants.REQ_ALIGN);
		this.diagram = diagram;
		this.alignment = alignment;
		this.isHorizantal = isHorizantal;
	}
	
	public boolean isHorizantal() {
		return isHorizantal;
	}

	public float getAlignment() {
		return alignment;
	}

	public StateMachineDiagram getDiagram() {
		return diagram;
	}
}
