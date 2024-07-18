package com.xrosstools.xstate.idea.editor.platform;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.XmlElementFactory;
import com.intellij.psi.xml.XmlText;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class TagClassReference extends PsiReferenceBase implements PsiReference {
    private String className;
    private String packageName;
    public TagClassReference(PsiElement element, String className, TextRange range) {
        super(element, range);
        this.className = className;
        packageName = className.substring(0, className.lastIndexOf("."));
    }

    @NotNull
    @Override
    public PsiElement resolve() {
        return ReferenceUtil.findClass(getElement().getProject(), className);
    }

    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
        String newValue = getElement().getText().replace(className, packageName + '.'+ newElementName);
        XmlText newText = XmlElementFactory.getInstance(getElement().getProject()).createDisplayText(newValue);
        getElement().replace(newText);
        return newText;
    }

    public Object[] getVariants() {
        return new Object[0];
    }
}
