package com.xrosstools.xstate.idea.editor.parts;

import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.OpenSourceUtil;
import com.xrosstools.xstate.idea.editor.commands.Accessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImplementationFinder {
    public void openImpl(Project project, ImplementationSource source) {
        String className = source.getImplementation();
        GlobalSearchScope scope = GlobalSearchScope.allScope (project);

        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, scope);
        if (null == psiClass) {
            Messages.showErrorDialog("Can not open " + className, "Error");
        }else
            OpenSourceUtil.navigate(psiClass);
    }

    public String assignImpl(Project project, String currentImpl) {
        TreeClassChooser chooser = TreeClassChooserFactory.getInstance(project).createAllProjectScopeChooser(currentImpl);
        PsiClass selected = null;
        chooser.showDialog();
        selected = chooser.getSelected();

        if(selected == null)
            return currentImpl;

        return selected.getQualifiedName();
    }

    public List<String> getMethods(Project project, String currentImpl) {
        GlobalSearchScope scope = GlobalSearchScope.allScope(project);
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(currentImpl, scope);

        if (psiClass == null)
            return Collections.emptyList();

        List<String> methods = new ArrayList<>();
        for (PsiMethod m : psiClass.getMethods()) {
            if (m.isConstructor()) continue;

            methods.add(m.getName());
        }

        return methods;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
}
