package com.xross.tools.xstate.editor.policies;


public class StateNodeDirectEditPolicy {//extends DirectEditPolicy{
//	private List<StateMachineFactor> factors;
//
//	public StateNodeDirectEditPolicy(List<StateMachineFactor> factors){
//		this.factors = factors;
//	}
//	
//    protected Command getDirectEditCommand(DirectEditRequest request) {
//    	String newFactor = (String) request.getCellEditor().getValue();
//    	StateMachineNode node = (StateMachineNode) getHost().getModel();
//    	for(StateMachineFactor factor: factors){
//    		if(factor.getFactorName().endsWith(newFactor))
//    			return new ChangeEventNameCommand(node, factors.indexOf(factor));
//    	}
//    	
//    	StateMachineFactor factor = new StateMachineFactor();
//    	factor.setFactorName(newFactor);
//    	return new AddFactorCommand(factors, node, factor);
//    }
//    protected void showCurrentEditValue(DirectEditRequest request) {
//        String value = (String) request.getCellEditor().getValue();
//        ((StateMachineNodeFigure) getHostFigure()).setFactor(value);
//    }
}
