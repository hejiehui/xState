package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractPanelContentProvider;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xstate.idea.editor.actions.GenerateFactoryAction;
import com.xrosstools.xstate.idea.editor.actions.GenerateTestAction;
import com.xrosstools.xstate.idea.editor.actions.StateMachineMessages;
import com.xrosstools.xstate.idea.editor.io.StateMachineDiagramFactory;
import com.xrosstools.xstate.idea.editor.io.XmlHelper;
import com.xrosstools.xstate.idea.editor.model.*;
import com.xrosstools.xstate.idea.editor.parts.StateMachinePartFactory;
import com.xrosstools.xstate.idea.editor.treeparts.StateMachineTreePartFactory;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;

public class StateMachinePanelContentProvider extends AbstractPanelContentProvider<StateMachineDiagram> implements StateMachineMessages {
    private Project project;
    private VirtualFile virtualFile;
    private StateMachineDiagram diagram;

    private GenerateFactoryAction generateFactoryAction;
    private GenerateTestAction generateTestAction;

    private StateMachineDiagramFactory factory = new StateMachineDiagramFactory();

    public StateMachinePanelContentProvider(Project project, VirtualFile virtualFile) {
        super(virtualFile);
        this.project = project;
        this.virtualFile = virtualFile;
        generateFactoryAction = new GenerateFactoryAction(project, virtualFile);
        generateTestAction = new GenerateTestAction(project, virtualFile.getNameWithoutExtension());
    }

    @Override
    public StateMachineDiagram getContent() throws Exception {
        diagram = factory.getFromDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(virtualFile.getInputStream()));

        //Make sure we always get the latest file
        generateFactoryAction.setDiagram(diagram);
        generateTestAction.setDiagram(diagram);

        return diagram;
    }

    @Override
    public void saveContent() throws Exception {
        String contentStr = XmlHelper.format(factory.writeToDocument(diagram));
        virtualFile.setBinaryContent(contentStr.getBytes(virtualFile.getCharset()));
    }

    @Override
    public ContextMenuProvider getContextMenuProvider() {
        return new StateMachineContextMenuProvider(project);
    }

    @Override
    public ContextMenuProvider getOutlineContextMenuProvider() {
        return new StateMachineOutlineContextMenuProvider(project);
    }

    private static Object[][] ENTRIES = new Object[][]{
            {"State Machine", StateMachine.class, StateMachineIcons.STATE_MACHINE},
            {"State Node", StateNode.class, StateMachineIcons.STATE_NODE},
            {"Start Node", StartNode.class, StateMachineIcons.START_NODE},
            {"End Node", EndNode.class, StateMachineIcons.END_NODE},
    };

    private static Object[][] CONN_ENTRIES = new Object[][]{
            {"Direct Route", RouteStyle.direct, StateMachineIcons.ROUTE_DIRECT},
            {"Height First Route", RouteStyle.heightFirst, StateMachineIcons.ROUTE_HEIGHT_FIRST},
            {"Width First Route", RouteStyle.widthFirst, StateMachineIcons.ROUTE_WIDTH_FIRST},
    };


    @Override
    public void buildPalette(JPanel palette) {
        for(Object[] entry: ENTRIES){
            palette.add(createNodeButton(entry));
        }

        for(Object[] entry: CONN_ENTRIES) {
            palette.add(createConnectionButton(entry));
        }

        palette.add(createPaletteButton(generateFactoryAction, StateMachineIcons.GENERATE_FACTORY, GENERATE_FACTORY));
        palette.add(createPaletteButton(generateTestAction, StateMachineIcons.GENERATE_TEST, GENERATE_TEST));

    }

    private JButton createConnectionButton(Object[] entry) {
        return createPaletteButton(e -> createConnection(entry[1]), (Icon)entry[2], (String)entry[0]);
    }

    private JButton createNodeButton(Object[] entry) {
        return createPaletteButton(e -> {
            try {
                createModel(((Class)entry[1]).newInstance());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }, (Icon)entry[2], (String)entry[0]);
    }

    @Override
    public EditPartFactory createEditPartFactory() {
        return new StateMachinePartFactory();
    }

    @Override
    public EditPartFactory createTreePartFactory() {
        return new StateMachineTreePartFactory();
    }
}
