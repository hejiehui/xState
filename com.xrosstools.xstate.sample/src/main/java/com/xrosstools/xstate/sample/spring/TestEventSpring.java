package com.xrosstools.xstate.sample.spring;

import com.xrosstools.xstate.Event;

public class TestEventSpring extends Event {
    private boolean pass;
    public TestEventSpring(boolean pass) {
        super("event");
        this.pass = pass;
    }

    public boolean isPass() {
        return pass;
    }
}
