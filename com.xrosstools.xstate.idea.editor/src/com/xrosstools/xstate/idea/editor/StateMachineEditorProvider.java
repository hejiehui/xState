package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractDiagramEditorProvider;
import com.xrosstools.idea.gef.PanelContentProvider;
import org.jetbrains.annotations.NotNull;

public class StateMachineEditorProvider extends AbstractDiagramEditorProvider {
    public static final String STATE_MACHINE_DIAGRAM = "state_machine_diagram";
    public static final String STATE_MACHINE = "state_machine";
    public static final String STATE_NODE = "state_node";
    public static final String START_NODE = "start_node";
    public static final String END_NODE = "end_node";
    public static final String ROUTE_DIRECT = "arrows_direct";
    public static final String ROUTE_HEIGHT_FIRST = "arrows_height";
    public static final String ROUTE_WIDTH_FIRST = "arrows_width";
    public static final String ENTRY_ACTION = "end_point";
    public static final String EXIT_ACTION = "start_point";
    public static final String TRANSITION_ACTION = "transition_action";

    @Override
    public FileType getFileType() {
        return StateMachineFileType.INSTANCE;
    }

    @Override
    public String getExtention() {
        return StateMachineFileType.EXTENSION;
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return "Xross State Machine Edtitor";
    }

    @Override
    public PanelContentProvider createPanelContentProvider(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return new StateMachinePanelContentProvider(project, virtualFile);
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
