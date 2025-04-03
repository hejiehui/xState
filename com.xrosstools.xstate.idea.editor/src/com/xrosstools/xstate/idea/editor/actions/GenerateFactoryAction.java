package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.actions.AbstractCodeGenerator;
import com.xrosstools.idea.gef.actions.CodeDisplayer;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.Event;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.idea.editor.model.StateNode;

import java.time.ZonedDateTime;

import static com.xrosstools.idea.gef.actions.CodeGenHelper.*;

public class GenerateFactoryAction extends AbstractCodeGenerator implements StateMachineMessages {
    private static final String CREATE_MACHINE =
            "    public static class %s {\n" +//state machine name, constants
            "%s" +
            "        public static StateMachine create() throws Exception {\n" +   //state machine
            "            return load().create(\"%s\");\n" +
            "        }\n" +
            "    }\n\n";

    private static final String INVALID_MACHINE_NAME =
            "    /*  Error!!! No. %d state machine name is empty */\n";

    private static final String MACHINE_COMMENTS =
            "    //%s\n";
    private static final String CONSTANT_COMMENTS =
                    "        //%s\n";
    private static final String CONSTANT_DEF =
                    "        public static final String %s = \"%s\";\n\n";//label = "Id"

    private static final String EVENT_DEF_HEADER =
            "        /*  Event Constants */\n";

    private static final String INVALID_EVENT_ID =
            "        /*  Error!!! No. %d event's Id is empty. */\n";

    private static final String STATE_DEF_HEADER =
            "        /*  State Constants */\n";

    private static final String INVALID_STATE_ID =
            "        /*  Error!!! No. %d event's Id is empty. */\n";

    private Project project;
    private VirtualFile file;
    private StateMachineDiagram diagram;

    public GenerateFactoryAction(Project project, VirtualFile file){
        super(project, "Generate model factory");
        this.project = project;
        this.file = file;
    }

    public void setDiagram(StateMachineDiagram diagram) {
        this.diagram = diagram;
    }

    public String getDefaultFileName() {
        return isEmpty(diagram.getName()) ?
                fileToClassName(file.getNameWithoutExtension()) :
                toClassName(diagram.getName());
    }

    @Override
    public String getContent(String packageName, String fileName) {
        StringBuffer codeBuf = getTemplate("/template/HelperTemplate.txt", this.getClass());
        replace(codeBuf, "!PACKAGE!", packageName);
        replace(codeBuf, "!DESCRIPTION!", getValue(diagram.getDescription()));
        replace(codeBuf, "!LAST_GENERATE_TIME!", ZonedDateTime.now().toString());
        replace(codeBuf, "!TEST_CLASS!", fileName);
        replace(codeBuf, "!MODEL_PATH!", findResourcesPath(project, file));

        replace(codeBuf, "!STATE_MACHINE_DEFINITIONS!", "\n" + generateBody().toString());

        return codeBuf.toString();
    }

    private StringBuffer generateBody() {
        StringBuffer constants = new StringBuffer();

        int i = 0;
        for(StateMachine sm: diagram.getMachines()) {
            i++;
            StringBuffer buf = new StringBuffer();
            if(isEmpty(sm.getName())) {
                constants.append(String.format(INVALID_MACHINE_NAME, i));
                continue;
            }

            int j = 0;
            buf.append(EVENT_DEF_HEADER);
            for(Event evt: sm.getEvents()) {
                j++;
                if(isEmpty(evt.getId())) {
                    buf.append(String.format(INVALID_EVENT_ID, j));
                    continue;
                }

                appendDesc(buf, CONSTANT_COMMENTS, evt.getDescription());
                appendLabelId(buf, evt.getDisplayText(), evt.getId());
            }

            int k = 0;
            buf.append(STATE_DEF_HEADER);
            for(StateNode s: sm.getNodes()) {
                k++;
                if(isEmpty(s.getId())) {
                    buf.append(String.format(INVALID_STATE_ID, k));
                    continue;
                }

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

    private boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public Command createCommand() {
        return null;
    }
}
