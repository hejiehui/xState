package com.xross.tools.xstate.editor;

import java.util.ArrayList;
import java.util.List;

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
    	{"State Machine", ProcessorNode.class, Activator.PROCESSOR},
    	{"State Node", ConverterNode.class, Activator.CONVERTER},
    	{},
    	{"Transition", ValidatorNode.class, Activator.VALIDATOR},
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
    		if(entry.length == 0){
//    	    	sep = new PaletteSeparator();
//    	    	sep.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
//    	    	entries.add(sep);
    	    	entries.add(new PaletteSeparator());
    		}else{
		    	entries.add(new CombinedTemplateCreationEntry(
		    			(String)entry[0],
		    			(String)entry[0],
		    			entry[1],
		    			new SimpleFactory((Class)entry[1]),
		    			Activator.getDefault().getImageRegistry().getDescriptor(((String)entry[2])),    			
		    			Activator.getDefault().getImageRegistry().getDescriptor(((String)entry[2]))));
    		}
    	}

    	controlGroup.addAll(entries);
        return controlGroup;
    }    

}
