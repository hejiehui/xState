package com.xrosstools.xstate.sample;

import com.xrosstools.xstate.Event;
import com.xrosstools.xstate.StateMachine;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static com.xrosstools.xstate.sample.MethodReference.*;

public class MethodReferenceTest {
	   /*  "test machine" test cases */
    /*  Test all output transitions of start */
    @Test
    public void testTestMachine_StartOnEvent() throws Exception {
        StateMachine sm = MethodUsage.create();
        sm.restore(MethodUsage.START);
        sm.notify(new Event(MethodUsage.EVENT));
        assertEquals(MethodUsage.S1, sm.getCurrentStateId());
    }

    /*  Test all output transitions of s1 */
    @Test
    public void testTestMachine_S1OnEvent() throws Exception {
        //Transition guard com.xrosstools.xstate.sample.MethodSupport::isTransitAllowedMethod will pass.
        StateMachine sm = MethodUsage.create();
        sm.restore(MethodUsage.S1);
        sm.notify(new TestEvent(true));
        assertEquals(MethodUsage.END, sm.getCurrentStateId());
    }

    @Test
    public void testTestMachine_S1OnEventFail() throws Exception {
        //Transition guard com.xrosstools.xstate.sample.MethodSupport::isTransitAllowedMethod will fail.
        StateMachine sm = MethodUsage.create();
        sm.restore(MethodUsage.S1);
        sm.notify(new TestEvent(false));
        assertEquals(MethodUsage.S1, sm.getCurrentStateId());
    }
}
