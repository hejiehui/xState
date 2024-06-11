package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.ui.Messages;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

import java.awt.event.ActionEvent;
import static com.xrosstools.xstate.idea.editor.actions.GenerateHelpertsAction.*;

public class GenerateTestAction extends Action {
    private static final String MACHINE_HEADER =
            "        /*  %s Test Cases */\n";//State machine name

    private static final String MACHINE_COMMENTS =
            "    //%s\n";

    private static final String NODE_HEADER =
            "        /*  Test all output transitions of %s */\n";//State machine name

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
        StringBuffer classBuf = GenerateHelpertsAction.getTemplate("/template/TestCaseTemplate.txt");
        replace(classBuf, "!PACKAGE!", diagram.getHelperPackage());
        replace(classBuf, "!TEST_CLASS!", toCamelCase(diagram.getName()));

        StringBuffer bodyBuf = new StringBuffer();
        for(StateMachine sm: diagram.getMachines()) {
            String machine = toVariable(sm.getName());
            String Machine = capitalize(machine);
            bodyBuf.append(String.format(MACHINE_HEADER, machine));
            appendDesc(bodyBuf, MACHINE_COMMENTS, sm.getDescription());

            for(StateNode stateNode: sm.getNodes()) {
                String state = toVariable(stateNode.getDisplayText());
                String State = capitalize(state);

                if(stateNode.getOutputs().size() != 0)
                    bodyBuf.append(String.format(NODE_HEADER, stateNode.getDisplayText()));

                for(StateTransition tran: stateNode.getOutputs()) {
                    String event = toVariable(tran.getEvent().getDisplayText());
                    String Event = capitalize(event);
                    String guard = tran.getTransitGuard() == null || tran.getTransitGuard().trim().length() == 0 ? null : tran.getTransitGuard().trim();

                    bodyBuf.append(String.format(TRANSITION_CASE_HEADER, Machine, State, Event, ""));

                    if(guard !=null)
                        bodyBuf.append(String.format(GUARD_PASS_COMMENT, tran.getTransitGuard()));
                    bodyBuf.append(String.format(TRANSITION_CASE_BODY, machine, state, event, tran.getTarget().getDisplayText()));

                    if(guard !=null) {
                        bodyBuf.append(String.format(GUARD_PASS_COMMENT, tran.getTransitGuard()));
                        bodyBuf.append(String.format(TRANSITION_CASE_HEADER, Machine, State, Event, "Fail"));
                        bodyBuf.append(String.format(GUARD_FAIL_COMMENT, tran.getTransitGuard()));

                        //The next should be the same as original state
                        bodyBuf.append(String.format(TRANSITION_CASE_BODY, machine, state, event, state));
                    }
                }
            }
        }
        replace(classBuf, "!TEST_BODY!", bodyBuf.toString());

        new CodeDisplayer("Generated test", classBuf.toString()).show();
    }

    @Override
    public Command createCommand() {
        return null;
    }
}