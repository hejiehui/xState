package com.xrosstools.xstate.idea.editor;

import com.intellij.ide.highlighter.XmlLikeFileType;
import com.intellij.lang.Language;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class StateMachineFileType extends XmlLikeFileType {
    public static final String NAME = "Xross State Machine File";
    public static final String DESCRIPTION = "Xross State Machine Model File";
    public static final String EXTENSION = "xstate";

    public static final StateMachineFileType INSTANCE = new StateMachineFileType();

    public StateMachineFileType() {
        super(XMLLanguage.INSTANCE);
    }

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
        return StateMachineIcons.STATE_MACHINE_DIAGRAM;
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
