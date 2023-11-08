package com.xrosstools.xstate.editor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;
import com.xrosstools.xstate.editor.model.StateTransition;

public class DeleteEventCommand extends Command{
    private StateMachine stateMachine;
    private Event event;
    private int index;
    private List<StateTransition> transitions;
    
    public DeleteEventCommand(
            StateMachine stateMachine, 
            Event event){
        this.stateMachine = stateMachine;
        this.event = event;
        index = stateMachine.getEvents().indexOf(event);
    }
    
    public void execute() {
        transitions = new ArrayList<StateTransition>();
        for(StateNode node: stateMachine.getNodes()){
            for(StateTransition tran: node.getOutputs()){
                if(tran.getEvent() == event) {
                    tran.setEvent(null);
                    transitions.add(tran);
                }
            }
        }

        stateMachine.removeEvent(event);
    }

    public String getLabel() {
        return "Delete Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        stateMachine.addEvent(index, event);
        for(StateTransition tran: transitions){
            tran.setEvent(event);
        }
    }
}
