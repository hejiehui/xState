package com.xrosstools.xstate.idea.editor.parts;

import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.OpenSourceUtil;

public class ImplementationFinder {
	public void openImpl(Project project, ImplementationSource source) {
		String className = source.getImplementation();
        GlobalSearchScope scope = GlobalSearchScope.allScope (project);

        //VirtualFileManager.getInstance().findFileByUrl("jar://path/to/file.jar!/path/to/file.class");

        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, scope);
        if (null == psiClass) {
            Messages.showErrorDialog("Can not open " + className, "Error");
        }else
            OpenSourceUtil.navigate(psiClass);
	}

	public String assignImpl(Project project, String currentImpl) {
		TreeClassChooser chooser = TreeClassChooserFactory.getInstance(project).createProjectScopeChooser("");
		chooser.showDialog();
		PsiClass selected = chooser.getSelected();
		if(selected == null)
			return null;

		return selected.getQualifiedName();
	}
	
	private boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}
}
