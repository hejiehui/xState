package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.actions.CodeDisplayer;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.Event;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.idea.editor.model.StateNode;

import java.awt.event.ActionEvent;
import java.time.ZonedDateTime;

import static com.xrosstools.idea.gef.actions.CodeGenHelper.*;

public class GenerateFactoryAction extends Action implements StateMachineMessages {
    private static final String CREATE_MACHINE =
            "    public static class %s {\n" +//state machine name, constants
            "%s" +
            "        public static StateMachine create() throws Exception {\n" +   //state machine
            "            return load().create(\"%s\");\n" +
            "        }\n" +
            "    }\n\n";

    private static final String MACHINE_COMMENTS =
            "    //%s\n";
    private static final String CONSTANT_COMMENTS =
                    "        //%s\n";
    private static final String CONSTANT_DEF =
                    "        public static final String %s = \"%s\";\n\n";//label = "Id"

    private static final String EVENT_DEF_HEADER =
            "        /*  Event Constants */\n";

    private static final String STATE_DEF_HEADER =
            "        /*  State Constants */\n";

    private Project project;
    private VirtualFile file;
    private StateMachineDiagram diagram;

    public GenerateFactoryAction(Project project, VirtualFile file, StateMachineDiagram diagram){
        this.project = project;
        this.file = file;
        this.diagram = diagram;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuffer codeBuf = getTemplate("/template/HelperTemplate.txt", this.getClass());
        replace(codeBuf, "!PACKAGE!", getValue(diagram.getHelperPackage()));
        replace(codeBuf, "!DESCRIPTION!", getValue(diagram.getDescription()));
        replace(codeBuf, "!LAST_GENERATE_TIME!", ZonedDateTime.now().toString());
        replace(codeBuf, "!TEST_CLASS!", toClassName(diagram.getName()));
        replace(codeBuf, "!MODEL_PATH!", findResourcesPath(project, file));

        replace(codeBuf, "!STATE_MACHINE_DEFINITIONS!", "\n" + generateBody().toString());

        new CodeDisplayer("Generated helper", codeBuf.toString()).show();
    }

    private StringBuffer generateBody() {
        StringBuffer constants = new StringBuffer();

        for(StateMachine sm: diagram.getMachines()) {
            StringBuffer buf = new StringBuffer(EVENT_DEF_HEADER);
            for(Event evt: sm.getEvents()) {
                appendDesc(buf, CONSTANT_COMMENTS, evt.getDescription());
                appendLabelId(buf, evt.getDisplayText(), evt.getId());
            }

            buf.append(STATE_DEF_HEADER);
            for(StateNode s: sm.getNodes()) {
                appendDesc(buf, CONSTANT_COMMENTS, s.getDescription());
                appendLabelId(buf, s.getDisplayText(), s.getId());
            }

            appendDesc(buf, MACHINE_COMMENTS, sm.getDescription());
            String createMachine = String.format(CREATE_MACHINE, toClassName(sm.getName()), buf.toString(), sm.getName());
            constants.append(createMachine);
        }

        return constants;
    }

    public static void appendDesc(StringBuffer buf, String template, String desc) {
        if(desc != null && desc.length() > 0) {
            buf.append(String.format(template, desc));
        }
    }

    private void appendLabelId(StringBuffer buf, String label, String id) {
        label = label.contains(" ") ? label.replace(' ', '_') : label;

        buf.append(String.format(CONSTANT_DEF, label.toUpperCase(), id));
    }

    public Command createCommand() {
        return null;
    }
}
