package com.xrosstools.xstate.sample.spring;

import com.xrosstools.xstate.StateMachine;
import com.xrosstools.xstate.StateMachineFactory;

public class MethodReferenceSpring {

    public static class MethodUsage {
        /*  Event Constants */
        public static final String EVENT = "event";

        /*  State Constants */
        public static final String START = "start";

        public static final String END = "end";

        public static final String S1 = "s1";

        public static StateMachine create() throws Exception {
            return load().create("method usage");
        }
    }
    
    private static volatile StateMachineFactory factory;
    private static StateMachineFactory load() throws Exception {
        if(factory == null) {
            synchronized(MethodReferenceSpring.class) {
                if(factory == null)
                    factory = StateMachineFactory.load("method_reference_spring.xstate");
            }
        }
        return factory;
    }
}
