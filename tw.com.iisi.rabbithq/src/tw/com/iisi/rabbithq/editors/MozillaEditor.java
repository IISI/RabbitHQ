package tw.com.iisi.rabbithq.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.osgi.framework.Bundle;

import tw.com.iisi.rabbithq.Activator;

/**
 * @author Chih-Liang Chang
 */
public class MozillaEditor extends EditorPart {

    public static final String ID = "tw.com.iisi.rabbithq.editors.MozillaEditor";

    private Browser browser;

    @Override
    public void doSave(IProgressMonitor monitor) {
        // TODO Auto-generated method stub

    }

    @Override
    public void doSaveAs() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        setSite(site);
        setInput(input);
    }

    @Override
    public boolean isDirty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSaveAsAllowed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void createPartControl(Composite parent) {
        try {
            browser = new Browser(parent, SWT.MOZILLA);
        } catch (Error e) {
            Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
            IStatus status = new Status(
                    IStatus.WARNING,
                    bundle.getSymbolicName(),
                    bundle.getState(),
                    "Cannot instantiate browser based on xulrunner. Change to default browser.",
                    e);
            Platform.getLog(bundle).log(status);
            browser = new Browser(parent, SWT.NONE);
        }

        browser.setUrl(getEditorInput().getName());
    }

    @Override
    public void setFocus() {
        // TODO Auto-generated method stub

    }

}
