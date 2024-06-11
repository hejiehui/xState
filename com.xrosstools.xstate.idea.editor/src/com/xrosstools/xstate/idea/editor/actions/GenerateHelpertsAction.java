package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.commands.Accessor;
import com.xrosstools.xstate.idea.editor.model.Event;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateMachineDiagram;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.parts.ImplementationFinder;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;

public class GenerateHelpertsAction extends Action implements StateMachineMessages {
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

    private VirtualFile file;
    private StateMachineDiagram diagram;
    private Accessor<String> accessor;
    private ImplementationFinder finder;

    public GenerateHelpertsAction(VirtualFile file, StateMachineDiagram diagram){
        this.file = file;
        this.diagram = diagram;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuffer codeBuf = getTemplate("/template/ConstantTemplate.txt");
        replace(codeBuf, "!PACKAGE!", diagram.getHelperPackage());
        replace(codeBuf, "!DESCRIPTION!", diagram.getDescription());
        replace(codeBuf, "!LAST_GENERATE_TIME!", ZonedDateTime.now().toString());
        replace(codeBuf, "!TEST_CLASS!", toCamelCase(diagram.getName()));
        replace(codeBuf, "!MODEL_PATH!", file.getPath());
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
            String createMachine = String.format(CREATE_MACHINE, sm.getName(), buf.toString(), sm.getName());
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

        buf.append(String.format(CONSTANT_DEF, label, id));
    }

    public Command createCommand() {
        return null;
    }

    // TODO This is copied from decision tree. and should be move to GEF.
    public static StringBuffer getTemplate(String filePath){
        StringBuffer codeBuf = new StringBuffer();

        BufferedReader reader = new BufferedReader(new InputStreamReader(GenerateHelpertsAction.class.getResourceAsStream(filePath)));
        String line;
        try {
            while((line = reader.readLine()) != null)
                codeBuf.append(line).append('\n');
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return codeBuf;
    }

    public static void replace(StringBuffer codeBuf, String replacementMark, String replacement){
        int start;
        while((start = codeBuf.indexOf(replacementMark)) >= 0) {
            codeBuf.replace(start, start + replacementMark.length(), replacement);
        }
    }

    public static String toVariable(String label) {
        return label.contains(" ") ? label.replace(' ', '_') : label;
    }

    public static String toCamelCase(String label) {
        StringBuffer clazz = new StringBuffer();
        for(String s: label.split(" ")) {
            clazz.append(capitalize(s));
        }
        return clazz.toString();
    }

    public static String capitalize(String label) {
        char[] charArray = label.toCharArray();
        if (charArray.length > 0) {
            charArray[0] = Character.toUpperCase(charArray[0]);
        }
        return new String(charArray);
    }
}
