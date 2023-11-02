package com.xrosstools.xstate.sample;

import com.xrosstools.xstate.Event;
import com.xrosstools.xstate.StateMachine;
import com.xrosstools.xstate.StateMachineFactory;

public class VipFlowTest {
    public static void main(String[] args) {
        try {
            StateMachineFactory f = StateMachineFactory.load("sample.xstate");
            StateMachine sm = f.create("vip flow");
            
            System.out.println("Start state: " + sm.getCurrentState().getId());
            notify(sm, "pass");
            notify(sm, "pass");
            notify(sm, "pass");
            notify(sm, "pass");
            notify(sm, "pass");
            
            System.out.println("End state: " + sm.getCurrentState().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void notify(StateMachine sm, String eventId) throws Exception {
        sm.notify(new Event(eventId));
        System.out.println(sm.getCurrentState().getId());
    }

}
