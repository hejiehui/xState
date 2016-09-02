package com.xrosstools.xstate.editor.parts;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.internal.ui.JavaUIMessages;
import org.eclipse.jdt.internal.ui.dialogs.OpenTypeSelectionDialog;
import org.eclipse.jdt.internal.ui.util.ExceptionHandler;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

@SuppressWarnings("restriction")
public class ImplementationFinder {
	// cached for next visit; TODO should be revised if there is only one match
	// for the name
	private Map<String, IType> s_sourceTypes = new HashMap<String, IType>();

	private IType getSourceType(String className) {
		if (className == null || className.trim().length() == 0)
			return null;
		return s_sourceTypes.get(className.trim());
	}

	private void setSourceType(IType type) {
		if (type == null)
			return;
		s_sourceTypes.put(type.getFullyQualifiedName(), type);
	}

	public void openImpl(ImplementationSource source) {
		String implClassName = source.getImplementation();
		if (getSourceType(implClassName) == null) {
			String newValue = assignImpl(implClassName);
			if (!isEmpty(newValue) && !newValue.equals(implClassName)){
				source.implChanged(newValue);
				implClassName = newValue;
			}
		}

		if (getSourceType(implClassName) == null)
			return;

		try {
			JavaUI.openInEditor(getSourceType(implClassName), true, true);
		} catch (CoreException x) {
			ExceptionHandler.handle(x,
					JavaUIMessages.OpenTypeAction_errorTitle,
					JavaUIMessages.OpenTypeAction_errorMessage);
		}
	}

	public String assignImpl(String currentImpl) {
		Shell parent = Display.getCurrent().getActiveShell();
		OpenTypeSelectionDialog dialog = new OpenTypeSelectionDialog(parent,
				true, PlatformUI.getWorkbench().getProgressService(), null,
				IJavaSearchConstants.TYPE);
		dialog.setTitle(JavaUIMessages.OpenTypeAction_dialogTitle);
		dialog.setMessage(JavaUIMessages.OpenTypeAction_dialogMessage);
		dialog.setInitialPattern(currentImpl, 2);

		int result = dialog.open();
		// if cancel clicked, will not change existing type;
		if (result != IDialogConstants.OK_ID)
			return currentImpl;

		Object[] types = dialog.getResult();
		if (types == null || types.length != 1)
			return null;

		setSourceType((IType) types[0]);

		return ((IType) types[0]).getFullyQualifiedName();
	}
	
	private boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}
}
