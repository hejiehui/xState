package com.xrosstools.xstate.idea.editor.platform;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;

public class AttributeClassReference extends PsiReferenceBase implements PsiReference {
    public AttributeClassReference(PsiElement element, TextRange range) {
        super(element, range);
    }

    @NotNull
    @Override
    public PsiElement resolve() {
        return getElement();
    }

    public Object[] getVariants() {
        return new Object[0];
    }
}
