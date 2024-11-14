package com.xrosstools.xstate.idea.editor.actions;

import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.actions.CodeDisplayer;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

import java.awt.event.ActionEvent;
import static com.xrosstools.idea.gef.actions.CodeGenHelper.*;


public class GenerateTestAction extends Action {
    private static final String MACHINE_HEADER =
            "    /*  \"%s\" test cases */\n";//State machine name

    private static final String MACHINE_COMMENTS =
            "    //%s\n";

    private static final String NODE_HEADER =
            "    /*  Test all output transitions of %s */\n";//State machine name

    private static final String TRANSITION_CASE_HEADER =
            "    @Test\n" +
            "    public void test%s_%sOn%s%s() throws Exception {\n";//Machine, State, Event, "" or Fail

    private static final String GUARD_PASS_COMMENT =
            "        //Transition guard %s will pass.\n "; //transition guard

    private static final String GUARD_FAIL_COMMENT =
            "        //Transition guard %s will fail.\n "; //transition guard

    private static final String TRANSITION_CASE_BODY =
            "        StateMachine sm = %1$s.create();\n" +                //machine
            "        sm.restore(%1$s.%2$s);\n" +                            //machine, state
            "        sm.notify(new Event(%1$s.%3$s));\n" +                     //event
            "        assertEquals(%1$s.%4$s, sm.getCurrentStateId());\n" + //machine, next node
            "    }\n\n";


    private StateMachineDiagram diagram;

    public GenerateTestAction(StateMachineDiagram diagram) {
        this.diagram = diagram;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuffer classBuf = getTemplate("/template/TestCaseTemplate.txt", this.getClass());
        replace(classBuf, "!PACKAGE!", getValue(diagram.getHelperPackage()));
        replace(classBuf, "!TEST_CLASS!", toClassName(diagram.getName()));

        StringBuffer bodyBuf = new StringBuffer();
        for(StateMachine sm: diagram.getMachines()) {
            String Machine = toClassName(sm.getName());
            bodyBuf.append(String.format(MACHINE_HEADER, sm.getName()));
            appendDesc(bodyBuf, MACHINE_COMMENTS, sm.getDescription());

            for(StateNode stateNode: sm.getNodes()) {
                String stateName = stateNode.getDisplayText();
                String state = toConstantName(stateName);
                String State = toClassName(stateName);

                if(stateNode.getOutputs().size() != 0)
                    bodyBuf.append(String.format(NODE_HEADER, stateNode.getDisplayText()));

                for(StateTransition tran: stateNode.getOutputs()) {
                    String eventName = tran.getEvent().getDisplayText();
                    String event = toConstantName(eventName);
                    String Event = toClassName(eventName);
                    String guard = tran.getTransitGuard() == null || tran.getTransitGuard().trim().length() == 0 ? null : tran.getTransitGuard().trim();

                    bodyBuf.append(String.format(TRANSITION_CASE_HEADER, Machine, State, Event, ""));

                    if(guard !=null)
                        bodyBuf.append(String.format(GUARD_PASS_COMMENT, tran.getTransitGuard()));
                    bodyBuf.append(String.format(TRANSITION_CASE_BODY, Machine, state, event, toConstantName(tran.getTarget().getDisplayText())));

                    if(guard !=null) {
                        bodyBuf.append(String.format(TRANSITION_CASE_HEADER, Machine, State, Event, "Fail"));
                        bodyBuf.append(String.format(GUARD_FAIL_COMMENT, tran.getTransitGuard()));

                        //The next should be the same as original state
                        bodyBuf.append(String.format(TRANSITION_CASE_BODY, Machine, state, event, state));
                    }
                }
            }
        }

        String codeBody = bodyBuf.toString();
        if(codeBody.endsWith("\n\n"))
            codeBody = codeBody.substring(0, codeBody.length()-2);

        replace(classBuf, "!TEST_BODY!", codeBody);

        new CodeDisplayer("Generated test", classBuf.toString()).show();
    }

    @Override
    public Command createCommand() {
        return null;
    }
}