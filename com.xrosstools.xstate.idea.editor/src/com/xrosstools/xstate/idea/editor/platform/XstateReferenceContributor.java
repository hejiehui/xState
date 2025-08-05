package com.xrosstools.xstate.idea.editor.platform;

import com.intellij.psi.PsiReferenceRegistrar;
import com.xrosstools.idea.gef.references.AbstractReferenceContributor;
import com.xrosstools.xstate.idea.editor.StateMachineDiagramConstants;
import com.xrosstools.xstate.idea.editor.StateMachineFileType;

public class XstateReferenceContributor extends AbstractReferenceContributor implements StateMachineDiagramConstants {
    public XstateReferenceContributor() {
        super(StateMachineFileType.EXTENSION);
    }

    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        registerTag(registrar, ENTRY_ACTION, true);
        registerTag(registrar, EXIT_ACTION, true);
        registerAttr(registrar, TRANSITION, TRANSIT_ACTION, true);
        registerAttr(registrar, TRANSITION, TRANSIT_GUARD, true);
    }
}
