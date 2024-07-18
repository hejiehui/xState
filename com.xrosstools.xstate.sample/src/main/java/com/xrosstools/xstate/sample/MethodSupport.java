package com.xrosstools.xstate.sample;

import com.xrosstools.xstate.Event;

public class MethodSupport {
    public void transitMethod(String sourceStateId,String targetStateId, Event event) {
        System.out.println(String.format("transitMethod from %s to %s on %s", sourceStateId, targetStateId, event.getId()));
    }

    public void exitMethod(String sourceStateId, Event event) {
        System.out.println(String.format("exitMethod: %s -[%s]->", sourceStateId, event.getId()));
    }

    private void enterMethod(String targetStateId, Event event) {
        System.out.println(String.format("enterMethod: -[%s]->%s", event.getId(), targetStateId));
    }

    private boolean isTransitAllowedMethod(String sourceStateId, String targetStateId, Event event) {
        System.out.println(String.format("isTransitAllowedMethod: from %s to %s on %s", sourceStateId, targetStateId, event.getId()));
        return ((TestEvent)event).isPass();
    }
}
