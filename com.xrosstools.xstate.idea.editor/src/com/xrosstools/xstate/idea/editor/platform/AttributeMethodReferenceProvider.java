package com.xrosstools.xstate.idea.editor.platform;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.xrosstools.xstate.idea.editor.platform.ReferenceUtil.*;

public class AttributeMethodReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
        String text = ((XmlAttributeValue)element).getValue();

        String methodName = getMethodName(text);
        String className = getClassName(text);

        //We only support rename non default method
        if(DEFAULT_METHOD.equals(methodName) || methodName == null || methodName.trim().length() == 0 || ReferenceUtil.findMethod(element.getProject(), className, methodName) == null) {
            return PsiReference.EMPTY_ARRAY;
        }

        // +1 because of the initial " in attribute value
        int start = className.length() + SEPARATOR.length() + 1;
        TextRange property = new TextRange(start, start + methodName.length());
        PsiReference methodRef = new AttributeMethodReference(element, className, methodName, property);

        return new PsiReference[]{methodRef};
    }
}
