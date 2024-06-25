package com.xrosstools.xstate.sample.spring;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.xrosstools.xstate.Event;
import com.xrosstools.xstate.StateMachine;
import com.xrosstools.xstate.XstateSpring;
import com.xrosstools.xstate.sample.spring.MethodReferenceSpring.MethodUsage;

@Configuration
@ComponentScan
public class MethodReferenceSpringTest {
    @Bean
    XstateSpring createSpringBeanFactory() {
        return new XstateSpring();
    }

    @BeforeClass
    public static void setup() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(MethodReferenceSpringTest.class);
    }
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
        sm.notify(new TestEventSpring(true));
        assertEquals(MethodUsage.END, sm.getCurrentStateId());
    }

    @Test
    public void testTestMachine_S1OnEventFail() throws Exception {
        //Transition guard com.xrosstools.xstate.sample.MethodSupport::isTransitAllowedMethod will fail.
        StateMachine sm = MethodUsage.create();
        sm.restore(MethodUsage.S1);
        sm.notify(new TestEventSpring(false));
        assertEquals(MethodUsage.S1, sm.getCurrentStateId());
    }
}
