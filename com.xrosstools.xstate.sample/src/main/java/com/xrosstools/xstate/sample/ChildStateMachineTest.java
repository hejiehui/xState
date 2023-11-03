package com.xrosstools.xstate.sample;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.xrosstools.xstate.Event;
import com.xrosstools.xstate.StateMachine;
import com.xrosstools.xstate.StateMachineFactory;

public class ChildStateMachineTest {
    private static StateMachineFactory f;
    @BeforeClass
    public static void setup() throws Exception {
        f = StateMachineFactory.load("sample.xstate");
    }

    private void testNotify(String id, String[][] eventStateSeq, boolean ended) throws Exception {
        StateMachine sm = f.create(id);
        for(String[] eventState: eventStateSeq) {
            sm.notify(new Event(eventState[0]));
            assertEquals(eventState[1], sm.getCurrentStateId());
        }
        assertEquals(sm.isEnded(), ended);
    }
        
    private void testRestore(String id, String stateId) throws Exception {
        StateMachine sm = f.create(id);

        sm.restore(stateId);
        assertEquals(sm.getCurrentStateId(), stateId);
    }
        
    @Test
    public void testNotifyA() throws Exception {
        testNotify("SA", new String[][] {
            new String[] {"E A1", "A1"},
            new String[] {"E A end", "end"},
            }, true);
    }
    
    @Test
    public void testNotifyB() throws Exception {
        testNotify("SB", new String[][] {
            new String[] {"E B1", "B1"},
            new String[] {"E B end", "end"},
            }, true);
    }
    
    @Test
    public void testNotifyA_A() throws Exception {
        testNotify("SA", new String[][] {
            new String[] {"E A-SA", "A-SA.start"},
            new String[] {"E A1", "A-SA.A1"},
            new String[] {"E A end", "A-SA.end"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SA", "A-SA.start"},
            new String[] {"E A1", "A-SA.A1"},
            new String[] {"E A end", "A-SA.end"},
            new String[] {"E A end", "end"},
            }, true);
    }
    
    @Test
    public void testNotifyB_B() throws Exception {
        testNotify("SB", new String[][] {
            new String[] {"E B-SB", "B-SB.start"},
            new String[] {"E B1", "B-SB.B1"},
            new String[] {"E B end", "B-SB.end"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SB", "B-SB.start"},
            new String[] {"E B1", "B-SB.B1"},
            new String[] {"E B end", "B-SB.end"},
            new String[] {"E B end", "end"},
            }, true);
    }
    
    @Test
    public void testNotifyA_B() throws Exception {
        //3 cases: reach start, reach middle, reach end
        testNotify("SA", new String[][] {
            new String[] {"E A1", "A1"},
            new String[] {"E A-SB", "A-SB.start"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B1", "A-SB.B1"},
            new String[] {"E B end", "A-SB.end"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B1", "A-SB.B1"},
            new String[] {"E B end", "A-SB.end"},
            new String[] {"E A end", "end"},
            }, true);

    }
    
    @Test
    public void testNotifyB_A() throws Exception {
        //3 cases: reach start, reach middle, reach end
        testNotify("SB", new String[][] {
            new String[] {"E B1", "B1"},
            new String[] {"E B-SA", "B-SA.start"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A1", "B-SA.A1"},
            new String[] {"E A end", "B-SA.end"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A1", "B-SA.A1"},
            new String[] {"E A end", "B-SA.end"},
            new String[] {"E B end", "end"},
            }, true);

    }
    
    @Test
    public void testNotifyA_B_A() throws Exception {
        //3 cases: reach start, reach middle, reach end
        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B-SA", "A-SB.B-SA.start"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B-SA", "A-SB.B-SA.start"},
            new String[] {"E A1", "A-SB.B-SA.A1"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B-SA", "A-SB.B-SA.start"},
            new String[] {"E A1", "A-SB.B-SA.A1"},
            new String[] {"E A end", "A-SB.B-SA.end"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B-SA", "A-SB.B-SA.start"},
            new String[] {"E A1", "A-SB.B-SA.A1"},
            new String[] {"E A end", "A-SB.B-SA.end"},
            new String[] {"E B end", "A-SB.end"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B-SA", "A-SB.B-SA.start"},
            new String[] {"E A1", "A-SB.B-SA.A1"},
            new String[] {"E A end", "A-SB.B-SA.end"},
            new String[] {"E B end", "A-SB.end"},
            new String[] {"E A end", "end"},
            }, true);
    }
    
    @Test
    public void testNotifyB_A_B() throws Exception {
        //3 cases: reach start, reach middle, reach end
        //3 cases: reach start, reach middle, reach end
        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A-SB", "B-SA.A-SB.start"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A-SB", "B-SA.A-SB.start"},
            new String[] {"E B1", "B-SA.A-SB.B1"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A-SB", "B-SA.A-SB.start"},
            new String[] {"E B1", "B-SA.A-SB.B1"},
            new String[] {"E B end", "B-SA.A-SB.end"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A-SB", "B-SA.A-SB.start"},
            new String[] {"E B1", "B-SA.A-SB.B1"},
            new String[] {"E B end", "B-SA.A-SB.end"},
            new String[] {"E A end", "B-SA.end"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A-SB", "B-SA.A-SB.start"},
            new String[] {"E B1", "B-SA.A-SB.B1"},
            new String[] {"E B end", "B-SA.A-SB.end"},
            new String[] {"E A end", "B-SA.end"},
            new String[] {"E B end", "end"},
            }, true);
    }
    
    @Test
    public void testRestoreA() throws Exception {
        testRestore("SA", "start");
        testRestore("SA", "A1");   
        testRestore("SA", "end");   
    }
    
    @Test
    public void testRestoreB() throws Exception {
        testRestore("SB", "start");
        testRestore("SB", "B1");   
        testRestore("SB", "end");   
    }
    
    @Test
    public void testRestoreA_A() throws Exception {
        testRestore("SA", "A-SA.start");
        testRestore("SA", "A-SA.A1");   
        testRestore("SA", "A-SA.end");   
    }

    @Test
    public void testRestoreB_B() throws Exception {
        testRestore("SB", "B-SB.start");
        testRestore("SB", "B-SB.B1");   
        testRestore("SB", "B-SB.end");   
    }
    
    
    @Test
    public void testRestoreABA() throws Exception {
        testRestore("SA", "A-SB.B-SA.start");
        testRestore("SA", "A-SB.B-SA.A1");   
        testRestore("SA", "A-SB.B-SA.end");   
    }

    @Test
    public void testRestoreBAB() throws Exception {
        testRestore("SB", "B-SA.A-SA.start");
        testRestore("SB", "B-SA.A-SA.A1");   
        testRestore("SB", "B-SA.A-SA.end");   
    }
}
