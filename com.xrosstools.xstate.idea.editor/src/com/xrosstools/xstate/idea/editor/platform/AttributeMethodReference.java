package com.xrosstools.xstate.idea.editor.platform;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.Nullable;

public class AttributeMethodReference extends PsiReferenceBase implements PsiReference{
    private String className;
    private String methodName;

    public AttributeMethodReference(PsiElement element, String className, String methodName, TextRange range) {
        super(element, range);
        this.className = className;
        this.methodName = methodName;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return ReferenceUtil.findMethod(getElement().getProject(), className, methodName);
    }

    public Object[] getVariants() {
        return new Object[0];
    }
}
