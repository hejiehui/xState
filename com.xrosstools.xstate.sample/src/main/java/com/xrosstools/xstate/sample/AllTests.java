package com.xrosstools.xstate.sample;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.xrosstools.xstate.sample.spring.MethodReferenceSpringTest;

@RunWith(Suite.class)
@SuiteClasses({
	ChildStateMachineTest.class,
	MethodReferenceTest.class,
	MethodReferenceSpringTest.class,
})
public class AllTests {

}
