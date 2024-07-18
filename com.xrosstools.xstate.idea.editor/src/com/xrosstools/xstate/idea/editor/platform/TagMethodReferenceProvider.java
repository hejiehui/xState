package com.xrosstools.xstate.idea.editor.platform;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.xrosstools.xstate.idea.editor.platform.ReferenceUtil.*;
import static com.xrosstools.xstate.idea.editor.platform.TagClassReferenceProvider.findTextElement;

public class TagMethodReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
        String text = ((XmlTag)element).getValue().getTrimmedText();

        PsiElement target = findTextElement(element);

        String methodName = getMethodName(text);
        String className = getClassName(text);

        //We only support rename non default method
        if(DEFAULT_METHOD.equals(methodName) || methodName == null || methodName.trim().length() == 0 || ReferenceUtil.findMethod(element.getProject(), className, methodName) == null) {
            return PsiReference.EMPTY_ARRAY;
        }

        int start = 0 + className.length() + SEPARATOR.length();
        TextRange property = new TextRange(start, start + methodName.length()).shiftRight(target.getStartOffsetInParent());

        PsiReference methodRef = new TagMethodReference(element, className, methodName, property);

        return new PsiReference[]{methodRef};
    }
}
