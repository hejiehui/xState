package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.xrosstools.idea.gef.actions.AbstractCodeGenerator;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.actions.CodeDisplayer;
import com.xrosstools.idea.gef.actions.CodeGenHelper;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

import java.awt.event.ActionEvent;
import static com.xrosstools.idea.gef.actions.CodeGenHelper.*;


public class GenerateTestAction extends AbstractCodeGenerator {
    private static final String MACHINE_HEADER =
            "    /*  \"%s\" test cases */\n";//State machine name

    private static final String INVALID_MACHINE_HEADER =
            "    /*  Warning!!! No. %d state machine name is empty */\n";

    private static final String MACHINE_COMMENTS =
            "    //%s\n";

    private static final String NODE_HEADER =
            "    /*  Test all output transitions of %s */\n";//State node name

    private static final String INVALID_NODE_HEADER =
            "    /*  Warning!!! No. %d state node id is empty */\n";

    private static final String TRANSITION_CASE_HEADER =
            "    @Test\n" +
            "    public void test%s_%sOn%s%s() throws Exception {\n";//Machine, State, Event, "" or Fail

    private static final String INVALID_TRANSITION_EVENT =
            "    /*  Warning!!! No. %d transition has no event. */\n";

    private static final String INVALID_TRANSITION_TARGET =
            "    /*  Warning!!! Target node of No. %d transition is empty. */\n";

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
    private String modelFileName;

    public GenerateTestAction(Project project, String modelFileName) {
        super(project, "Generate test cases");
        this.modelFileName = modelFileName;
    }

    public void setDiagram(StateMachineDiagram diagram) {
        this.diagram = diagram;
    }

    public String getDefaultFileName() {
        return getFactoryName() + "Test";
    }

    private String getFactoryName() {
        return isEmpty(diagram.getName()) ?
                fileToClassName(modelFileName) :
                toClassName(diagram.getName());
    }

    @Override
    public String getContent(String packageName, String fileName) {
        PsiClass factoryClass = chooseClass("Select model factory", getFactoryName());
        if(factoryClass == null) return  null;

        StringBuffer classBuf = getTemplate("/template/TestCaseTemplate.txt", this.getClass());
        replace(classBuf, "!PACKAGE!", packageName);
        replace(classBuf, "!TEST_CLASS!", fileName);
        replace(classBuf, "!FACTORY_CLASS!", factoryClass.getQualifiedName());

        StringBuffer bodyBuf = new StringBuffer();
        int i = 0;
        for(StateMachine sm: diagram.getMachines()) {
            i++;
            String smName = sm.getName();
            if(isEmpty(smName)) {
                bodyBuf.append(String.format(INVALID_MACHINE_HEADER, i));
                continue;
            }

            String Machine = toClassName(smName);
            bodyBuf.append(String.format(MACHINE_HEADER, smName));
            appendDesc(bodyBuf, MACHINE_COMMENTS, sm.getDescription());

            int j = 0;
            for(StateNode stateNode: sm.getNodes()) {
                j++;
                if(isEmpty(stateNode.getId())) {
                    bodyBuf.append(String.format(INVALID_NODE_HEADER, j));
                    continue;
                }

                String stateName = stateNode.getDisplayText();
                String state = toConstantName(stateName);
                String State = toClassName(stateName);

                if(stateNode.getOutputs().size() != 0)
                    bodyBuf.append(String.format(NODE_HEADER, stateNode.getDisplayText()));

                int k = 0;
                for(StateTransition tran: stateNode.getOutputs()) {
                    k++;
                    if(tran.getEvent() == null || isEmpty(tran.getEvent().getId())) {
                        bodyBuf.append(String.format(INVALID_TRANSITION_EVENT, k));
                        continue;
                    }

                    if(isEmpty(tran.getTarget().getId())) {
                        bodyBuf.append(String.format(INVALID_TRANSITION_TARGET, k));
                        continue;
                    }

                    String eventName = tran.getEvent().getDisplayText();
                    String event = toConstantName(eventName);
                    String Event = toClassName(eventName);
                    String guard = tran.getTransitGuard() == null || tran.getTransitGuard().trim().length() == 0 ? null : tran.getTransitGuard().trim();

                    bodyBuf.append(String.format(TRANSITION_CASE_HEADER, Machine, State, Event, ""));

                    if(guard != null)
                        bodyBuf.append(String.format(GUARD_PASS_COMMENT, tran.getTransitGuard()));
                    bodyBuf.append(String.format(TRANSITION_CASE_BODY, Machine, state, event, toConstantName(tran.getTarget().getDisplayText())));

                    if(guard != null) {
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

        return classBuf.toString();
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    @Override
    public Command createCommand() {
        return null;
    }
}