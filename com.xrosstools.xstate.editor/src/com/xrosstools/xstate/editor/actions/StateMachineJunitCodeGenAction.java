package com.xrosstools.xstate.editor.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.xrosstools.xstate.editor.StateMachineDiagramGraphicalEditor;
import com.xrosstools.xstate.editor.codegen.StateMachineJunitTestCodeGen;
import com.xrosstools.xstate.editor.model.StateMachineDiagram;

public class StateMachineJunitCodeGenAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages{
	private StateMachineJunitTestCodeGen junitCodeGen = new StateMachineJunitTestCodeGen();
	public StateMachineJunitCodeGenAction(IWorkbenchPart part){
		super(part);
		setId(ID_PREFIX + GEN_JUNIT_TEST_CODE);
		setText(GEN_JUNIT_TEST_CODE_MSG);
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		StateMachineDiagramGraphicalEditor editor = (StateMachineDiagramGraphicalEditor)getWorkbenchPart();
		IFile file = ((IFileEditorInput)editor.getEditorInput()).getFile();

		String packageName, testName, path;
		packageName = "com.ebay.xdomain.xcomponent";
		testName = file.getName().substring(0, file.getName().indexOf(".xstate")) + "Test";
		
		path = file.getName();
//		file.getProjectRelativePath()
		StateMachineDiagram diagram = (StateMachineDiagram)editor.getRootEditPart().getContents().getModel();
		String out = junitCodeGen.generate(diagram, packageName, testName, path);
		print(out);
	}
	
	private static final String CONSOLE_NAME = "Decision tree editor[Xross tools]";
	
	public void print(String text){
		try {
			ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
			IConsoleManager manager = consolePlugin.getConsoleManager();
			MessageConsole console = null;
			for (IConsole c : manager.getConsoles()) {
				if (c.getName().equals(CONSOLE_NAME)) {
					console = (MessageConsole) c;
					break;
				}
			}
			if (console == null) {
				console = new MessageConsole(CONSOLE_NAME, null);
				manager.addConsoles(new IConsole[] { console });
				console.activate();
			}
			
			MessageConsoleStream stream = console.newMessageStream();
			stream.setColor(new Color(null, 0x33, 0x99, 0xcc));
			stream.println(text);
			stream.flush();
			stream.close();
			manager.refresh(stream.getConsole());

		} catch (Exception e) {
			e.printStackTrace();
			//PluginLogHelper.log(Activator.getDefault(), Activator.PLUGIN_ID, IStatus.WARNING, "Unable to initialize console: " + e.getMessage(), e);
		}

	}
}
