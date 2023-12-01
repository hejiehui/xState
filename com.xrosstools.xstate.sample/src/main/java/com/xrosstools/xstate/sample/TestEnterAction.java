package com.xrosstools.xstate.sample;

import com.xrosstools.xstate.EntryAction;
import com.xrosstools.xstate.Event;

public class TestEnterAction implements EntryAction {
    public void enter(String targetStateId, Event event) {
        System.out.println(String.format("Now enter from [%s] to [%s]", event.getId(), targetStateId));
    }
}
