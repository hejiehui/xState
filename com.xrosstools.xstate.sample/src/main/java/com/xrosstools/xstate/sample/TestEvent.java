package com.xrosstools.xstate.sample;

import com.xrosstools.xstate.Event;

public class TestEvent extends Event {
    private boolean pass;
    public TestEvent(boolean pass) {
        super("event");
        this.pass = pass;
    }

    public boolean isPass() {
        return pass;
    }
}
