package com.xrosstools.xstate.idea.editor.actions;

import com.xrosstools.idea.gef.actions.codegen.GeneratorFactory;
import com.xrosstools.idea.gef.actions.codegen.SimpleImplGenerator;
import com.xrosstools.xstate.idea.editor.model.StateMachineConstants;

import java.util.HashMap;
import java.util.Map;

public class StateCodeGenFactory extends GeneratorFactory<String> implements StateMachineConstants {
    public static final String ENTRY_ACTION = PROP_ENTRY_ACTION;
    public static final String EXIT_ACTION = PROP_EXIT_ACTION;
    public static final String TRANSITION_ACTION = PROP_TRANSITION_ACTION;
    public static final String TRANSITION_GUARD = PROP_TRANSITION_GUARD;

    private static Map<String, String> pairs = new HashMap<>();

    private static final String IMPORTS = "com.xrosstools.xstate.*";

    private static final String ENTRY_ACTION_BODY =
                    "    @Override\n" +
                    "    public void enter(String targetStateId, Event event) {\n" +
                    "    }";

    private static final String EXIT_ACTION_BODY =
                    "    @Override\n" +
                    "    public void exit(String sourceStateId, Event event) {\n" +
                    "    }";

    private static final String TRANSITION_ACTION_BODY =
                    "    @Override\n" +
                    "    public void transit(String sourceStateId, String targetStateId, Event event) {\n" +
                    "    }";

    private static final String TRANSITION_GUARD_BODY =
                    "    @Override\n" +
                    "    public boolean isTransitAllowed(String sourceId, String targetId, Event event) {\n" +
                    "        return false;\n" +
                    "    }";

    public static final StateCodeGenFactory INSTANCE = new StateCodeGenFactory();

    public StateCodeGenFactory() {
        register(ENTRY_ACTION, new SimpleImplGenerator().setInterfaceName("EntryAction").setMethods(ENTRY_ACTION_BODY).setImports(IMPORTS));
        register(EXIT_ACTION, new SimpleImplGenerator().setInterfaceName("ExitAction").setMethods(EXIT_ACTION_BODY).setImports(IMPORTS));
        register(TRANSITION_ACTION, new SimpleImplGenerator().setInterfaceName("TransitAction").setMethods(TRANSITION_ACTION_BODY).setImports(IMPORTS));
        register(TRANSITION_GUARD, new SimpleImplGenerator().setInterfaceName("TransitionGuard").setMethods(TRANSITION_GUARD_BODY).setImports(IMPORTS));
    }

    static {
        pairs.put(ENTRY_ACTION, EXIT_ACTION);
        pairs.put(EXIT_ACTION, ENTRY_ACTION);
        pairs.put(TRANSITION_ACTION, TRANSITION_GUARD);
        pairs.put(TRANSITION_GUARD, TRANSITION_ACTION);
    }

    public static String getPair(String classType) {
        return pairs.get(classType);
    }
}
