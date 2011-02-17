package tw.com.iisi.rabbithq.handler.java;

import org.eclipse.swt.browser.Browser;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import tw.com.iisi.rabbithq.editors.IJavaHandler;

public class CloseBrowserHandler implements IJavaHandler {

    @Override
    public Object execute(Browser browser, Object[] args) {
        IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor();
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .closeEditor(part, false);
        return null;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

}
