package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractPanelContentProvider;
import com.xrosstools.idea.gef.Activator;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditContext;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.idea.gef.util.XmlHelper;
import com.xrosstools.xstate.idea.editor.io.StateMachineDiagramFactory;
import com.xrosstools.xstate.idea.editor.model.*;
import com.xrosstools.xstate.idea.editor.parts.StateMachinePartFactory;
import com.xrosstools.xstate.idea.editor.treeparts.StateMachineTreePartFactory;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;

public class StateMachinePanelContentProvider extends AbstractPanelContentProvider<StateMachineDiagram> implements StateMachineDiagramConstants {
    private Project project;
    private VirtualFile virtualFile;
    private StateMachineDiagram diagram;

    private StateMachineDiagramFactory factory = new StateMachineDiagramFactory();

    public StateMachinePanelContentProvider(Project project, VirtualFile virtualFile) {
        super(virtualFile);
        this.project = project;
        this.virtualFile = virtualFile;
    }

    @Override
    public StateMachineDiagram getContent() throws Exception {
        diagram = factory.getFromDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(virtualFile.getInputStream()));
        return diagram;
    }

    @Override
    public void saveContent() throws Exception {
        String contentStr = XmlHelper.format(factory.writeToDocument(diagram));
        virtualFile.setBinaryContent(contentStr.getBytes(virtualFile.getCharset()));
    }

    @Override
    public ContextMenuProvider getContextMenuProvider() {
        return new StateMachineContextMenuProvider(project, this);
    }

    @Override
    public ContextMenuProvider getOutlineContextMenuProvider() {
        return new StateMachineOutlineContextMenuProvider(project, this);
    }

    private static Object[][] ENTRIES = new Object[][]{
            {"State Machine", StateMachine.class, StateMachineEditorProvider.STATE_MACHINE},
            {"State Node", StateNode.class, StateMachineEditorProvider.STATE_NODE},
            {"Start Node", StartNode.class, StateMachineEditorProvider.START_NODE},
            {"End Node", EndNode.class, StateMachineEditorProvider.END_NODE},
    };

    private static Object[][] CONN_ENTRIES = new Object[][]{
            {"Direct Route", RouteStyle.direct, StateMachineEditorProvider.ROUTE_DIRECT},
            {"Height First Route", RouteStyle.heightFirst, StateMachineEditorProvider.ROUTE_HEIGHT_FIRST},
            {"Width First Route", RouteStyle.widthFirst, StateMachineEditorProvider.ROUTE_WIDTH_FIRST},
    };


    @Override
    public void buildPalette(JPanel palette) {
        for(Object[] entry: ENTRIES){
            palette.add(createNodeButton(entry));
        }

        for(Object[] entry: CONN_ENTRIES) {
            palette.add(createConnectionButton(entry));
        }
    }

    private JButton createConnectionButton(Object[] entry) {
        JButton btn = new JButton((String)entry[0], IconLoader.findIcon(Activator.getIconPath((String)entry[2])));
        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> createConnection(entry[1]));
        return btn;
    }

    private JButton createNodeButton(Object[] entry) {
        JButton btn = new JButton((String)entry[0], IconLoader.findIcon(Activator.getIconPath((String)entry[2])));
        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> {
            try {
                createModel(((Class)entry[1]).newInstance());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        return btn;
    }

    @Override
    public void buildToolbar(JToolBar toolbar) {
    }

    @Override
    public EditPartFactory createEditPartFactory(EditContext context) {
        return new StateMachinePartFactory(context);
    }

    @Override
    public EditPartFactory createTreePartFactory(EditContext context) {
        return new StateMachineTreePartFactory(context);
    }
}
