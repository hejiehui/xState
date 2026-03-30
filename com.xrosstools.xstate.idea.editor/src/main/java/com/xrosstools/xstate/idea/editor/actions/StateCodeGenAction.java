package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.CodeGenHelper;
import com.xrosstools.idea.gef.actions.codegen.AbstractOptionalGeneratorAction;
import com.xrosstools.idea.gef.actions.codegen.ImplementationGenerator;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.commands.CommandChain;
import com.xrosstools.idea.gef.commands.PropertyChangeCommand;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.xstate.idea.editor.model.StateNode;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

import java.util.ArrayList;
import java.util.List;

public class StateCodeGenAction extends AbstractOptionalGeneratorAction {
    private static StateCodeGenFactory genFactory = StateCodeGenFactory.INSTANCE;
    public StateCodeGenAction(Project project, IPropertySource source, String classType) {
        super(project, source, classType);
    }

    @Override
    public String getDefaultClassName(IPropertySource source, String classType) {
        String name = null;
        if(source instanceof StateNode)
            name = ((StateNode)source).getId();
        else {
            StateTransition transition = (StateTransition)source;
            name = transition.getSource().getId() + " to " + transition.getTarget().getId();
        }

        name += classType;
        return CodeGenHelper.toClassName(name);
    }

    @Override
    public String getOptionMessage(IPropertySource source, String classType) {
        return "Also implement the following";
    }

    @Override
    public String[] getOptions(IPropertySource source, String classType) {
        return new String[]{StateCodeGenFactory.getPair(classType)};
    }

    @Override
    public List<ImplementationGenerator> getGenerators(IPropertySource source, String classType, List<String> selectedOptions) {
        List<ImplementationGenerator> generators = new ArrayList<>();
        generators.add(genFactory.getGenerator(classType));
        if(selectedOptions.size() == 1)
            generators.add(genFactory.getGenerator(selectedOptions.get(0)));
        return generators;
    }

    public Command createCommand(IPropertySource source, String classType, String fullClassName, List<String> selectedOptions) {
        Command cmd = new PropertyChangeCommand(source, classType, fullClassName);
        if(selectedOptions.size() == 0)
            return cmd;

        CommandChain cc = new CommandChain();
        cc.add(cmd);
        cc.add(new PropertyChangeCommand(source, selectedOptions.get(0), fullClassName));
        return cc;
    }

}
