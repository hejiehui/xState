package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractDiagramEditorProvider;
import com.xrosstools.idea.gef.PanelContentProvider;
import org.jetbrains.annotations.NotNull;

public class StateMachineEditorProvider extends AbstractDiagramEditorProvider {
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
