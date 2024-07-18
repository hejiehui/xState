package com.xrosstools.xstate.idea.editor.platform;

import com.intellij.patterns.XmlAttributeValuePattern;
import com.intellij.patterns.XmlElementPattern;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.xrosstools.xstate.idea.editor.StateMachineDiagramConstants;
import com.xrosstools.xstate.idea.editor.StateMachineFileType;

import static com.intellij.patterns.StandardPatterns.string;
import static com.intellij.patterns.XmlPatterns.xmlFile;

public class XstateReferenceContributor extends PsiReferenceContributor implements StateMachineDiagramConstants {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        registerTag(registrar, ENTRY_ACTION);
        registerTag(registrar, EXIT_ACTION);
        registerAttr(registrar, TRANSITION, TRANSIT_ACTION);
        registerAttr(registrar, TRANSITION, TRANSIT_GUARD);
    }

    private void registerTag(PsiReferenceRegistrar registrar, String tagName) {
        XmlElementPattern pattern = XmlPatterns.xmlTag()
                .withName(tagName)
                .inFile(xmlFile().withName(string().endsWith("." + StateMachineFileType.EXTENSION)));

        registrar.registerReferenceProvider(pattern, new TagClassReferenceProvider());
        registrar.registerReferenceProvider(pattern, new TagMethodReferenceProvider());

    }

    private void registerAttr(PsiReferenceRegistrar registrar, String parentTagName, String attribute) {
        XmlAttributeValuePattern pattern = XmlPatterns.xmlAttributeValue()
                .withParent(
                        XmlPatterns.xmlAttribute().withName(attribute)
                                .withParent(XmlPatterns.xmlTag().withName(parentTagName)))
                .inFile(xmlFile().withName(string().endsWith("." + StateMachineFileType.EXTENSION)));

        registrar.registerReferenceProvider(pattern, new AttributeClassReferenceProvider());
        registrar.registerReferenceProvider(pattern, new AttributeMethodReferenceProvider());
    }
}
