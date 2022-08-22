package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class StateMachineFileType implements FileType {
    public static final String NAME = "Xross State Machine File";
    public static final String DESCRIPTION = "Xross State Machine Model File";
    public static final String EXTENSION = "xstate";
    public static final String ICON = "state_machine_diagram";

    public static final StateMachineFileType INSTANCE = new StateMachineFileType();

    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return IconLoader.findIcon(com.xrosstools.idea.gef.Activator.getIconPath(ICON));
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return null;
    }
}
