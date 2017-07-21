package com.xrosstools.xstate.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;

import com.xrosstools.xstate.editor.model.EndNode;
import com.xrosstools.xstate.editor.model.RouteStyle;
import com.xrosstools.xstate.editor.model.StartNode;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateNode;

public class StateMachinePaletteFactory {
	private Class<StateMachinePaletteFactory> imageClass = StateMachinePaletteFactory.class;
    public PaletteRoot createPalette() {
        PaletteRoot paletteRoot = new PaletteRoot();
        paletteRoot.addAll(createCategories(paletteRoot));    	
        return paletteRoot;
    }
    
    private List<PaletteContainer> createCategories(PaletteRoot root) {
        List<PaletteContainer> categories = new ArrayList<PaletteContainer>();

        categories.add(createControlGroup(root));

        return categories;
    }
    
    private static Object[][] ENTRIES = new Object[][]{
    	{"State Machine", StateMachine.class, Activator.STATE_MACHINE},
    	{"State Node", StateNode.class, Activator.STATE_NODE},
    	{"Start Node", StartNode.class, Activator.START_NODE},
    	{"End Node", EndNode.class, Activator.END_NODE},
    };

    private static Object[][] CONN_ENTRIES = new Object[][]{
        {"Direct Route", RouteStyle.direct, Activator.ROUTE_DIRECT},
        {"Height First Route", RouteStyle.heightFirst, Activator.ROUTE_HEIGHT_FIRST},
        {"Width First Route", RouteStyle.widthFirst, Activator.ROUTE_WIDTH_FIRST},
    };
    
    private PaletteContainer createControlGroup(PaletteRoot root) {
        PaletteGroup controlGroup = new PaletteGroup("Control Group");
        List<PaletteEntry> entries = new ArrayList<PaletteEntry>();

        ToolEntry tool = new SelectionToolEntry();
        entries.add(tool);
        root.setDefaultEntry(tool);

    	tool = new MarqueeToolEntry();
    	entries.add(tool);

    	PaletteSeparator sep = new PaletteSeparator();
    	sep.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
    	entries.add(sep);

    	for(Object[] entry: ENTRIES){
	    	entries.add(new CombinedTemplateCreationEntry(
	    			(String)entry[0],
	    			(String)entry[0],
	    			entry[1],
	    			new SimpleFactory((Class)entry[1]),
	    			Activator.getDefault().getImageRegistry().getDescriptor(((String)entry[2])),    			
	    			Activator.getDefault().getImageRegistry().getDescriptor(((String)entry[2]))));
    	}

    	entries.add(new PaletteSeparator());
        for(final Object[] entry: CONN_ENTRIES) {
            entries.add(new ConnectionCreationToolEntry(
                    (String)entry[0],
                    (String)entry[0],
                    new CreationFactory() {
                        public Object getNewObject() { return entry[1];}
                        public Object getObjectType() { return entry[1];}
                    },
                    Activator.getDefault().getImageRegistry().getDescriptor(((String)entry[2])),                
                    Activator.getDefault().getImageRegistry().getDescriptor(((String)entry[2]))));
        }
    	controlGroup.addAll(entries);
        return controlGroup;
    }    

}
