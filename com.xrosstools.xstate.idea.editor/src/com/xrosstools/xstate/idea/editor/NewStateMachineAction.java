package com.xrosstools.xstate.idea.editor;

import com.xrosstools.idea.gef.DefaultNewModelFileAction;
import com.xrosstools.idea.gef.actions.CodeGenHelper;

public class NewStateMachineAction extends DefaultNewModelFileAction {
    private static final String modelTypeName = "Xross State Machine";
    private static final String templatePath = "/template/emptyTemplate.xstate";
    private static final String newFileName = "new_xstate_file";

    private static final String NAME_TEMPLATE = "!NAME!";
    public NewStateMachineAction() {
        super(modelTypeName, StateMachineFileType.EXTENSION, StateMachineIcons.STATE_MACHINE_DIAGRAM, newFileName, templatePath);
    }

    public String createTemplateFor(String fileName) {
        StringBuffer template = new StringBuffer(getTemplate());
        CodeGenHelper.replace(template, NAME_TEMPLATE, fileName.replace('_', ' '));
        return template.toString();
    }
}
