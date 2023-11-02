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
        
    @Test
    public void testA() throws Exception {
        testNotify("SA", new String[][] {
            new String[] {"E A1", "A1"},
            new String[] {"E A end", "end"},
            }, true);
    }
    
    @Test
    public void testB() throws Exception {
        testNotify("SB", new String[][] {
            new String[] {"E B1", "B1"},
            new String[] {"E B end", "end"},
            }, true);
    }
    
    @Test
    public void testA_B() throws Exception {
        //3 cases: reach start, reach middle, reach end
        testNotify("SA", new String[][] {
            new String[] {"E A1", "A1"},
            new String[] {"E A-SB", "A-SB.start"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B1", "A-SB.B1"},
            new String[] {"E B end", "A-SB"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B1", "A-SB.B1"},
            new String[] {"E B end", "A-SB"},
            new String[] {"E A end", "end"},
            }, true);

    }
    
    @Test
    public void testB_A() throws Exception {
        //3 cases: reach start, reach middle, reach end
        testNotify("SB", new String[][] {
            new String[] {"E B1", "B1"},
            new String[] {"E B-SA", "B-SA.start"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A1", "B-SA.A1"},
            new String[] {"E A end", "B-SA"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A1", "B-SA.A1"},
            new String[] {"E A end", "B-SA"},
            new String[] {"E B end", "end"},
            }, true);

    }
    
    @Test
    public void testA_B_A() throws Exception {
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
            new String[] {"E A end", "A-SB.B-SA"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B-SA", "A-SB.B-SA.start"},
            new String[] {"E A1", "A-SB.B-SA.A1"},
            new String[] {"E A end", "A-SB.B-SA"},
            new String[] {"E B end", "A-SB"},
            }, false);

        testNotify("SA", new String[][] {
            new String[] {"E A-SB", "A-SB.start"},
            new String[] {"E B-SA", "A-SB.B-SA.start"},
            new String[] {"E A1", "A-SB.B-SA.A1"},
            new String[] {"E A end", "A-SB.B-SA"},
            new String[] {"E B end", "A-SB"},
            new String[] {"E A end", "end"},
            }, true);
    }
    
    @Test
    public void testB_A_B() throws Exception {
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
            new String[] {"E B end", "B-SA.A-SB"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A-SB", "B-SA.A-SB.start"},
            new String[] {"E B1", "B-SA.A-SB.B1"},
            new String[] {"E B end", "B-SA.A-SB"},
            new String[] {"E A end", "B-SA"},
            }, false);

        testNotify("SB", new String[][] {
            new String[] {"E B-SA", "B-SA.start"},
            new String[] {"E A-SB", "B-SA.A-SB.start"},
            new String[] {"E B1", "B-SA.A-SB.B1"},
            new String[] {"E B end", "B-SA.A-SB"},
            new String[] {"E A end", "B-SA"},
            new String[] {"E B end", "end"},
            }, true);
    }
    
    @Test
    public void testAToARestore() throws Exception {
    }

    @Test
    public void testAToBRestore() throws Exception {
    }
    
    
    @Test
    public void testBToBRestore() throws Exception {
    }

    @Test
    public void testBToARestore() throws Exception {
    }

    @Test
    public void testNotify() throws Exception {
        StateMachine sm = f.create("DB Health Lifecycle");
    }
}
