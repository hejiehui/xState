package com.xrosstools.xstate.idea.editor.platform;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;

public class ReferenceUtil {
    public static final String SEPARATOR = "::";
    public static final String DEFAULT_METHOD = "#default";

    public static String getClassName(String name) {
        return name.contains(SEPARATOR) ? name.split(SEPARATOR)[0] : name;
    }
    public static String getMethodName(String name) {
        return name.contains(SEPARATOR) ? name.split(SEPARATOR)[1] : DEFAULT_METHOD;
    }

    public static PsiClass findClass(Project project, String className) {
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));

        if (psiClass == null)
            return null;

        return psiClass;
    }

    public static PsiMethod findMethod(Project project, String className, String methodName) {
        PsiClass psiClass = findClass(project, className);
        if(psiClass == null)
            return null;

        PsiMethod[] methods = psiClass.findMethodsByName(methodName, false);

        return methods.length == 0 ? null : psiClass.findMethodsByName(methodName, false)[0];
    }
}
