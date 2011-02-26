package tw.com.iisi.rabbithq.editors;

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

import tw.com.iisi.rabbithq.Activator;

/**
 * @author Chih-Liang Chang
 */
public class MozillaEditor extends EditorPart {

    public static final String ID = "tw.com.iisi.rabbithq.editors.MozillaEditor";

    private Browser browser;

    private Set<ServiceReference> serviceRefs;

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

        browser.addTitleListener(new TitleListener() {

            @Override
            public void changed(TitleEvent event) {
                MozillaEditor.this.setPartName(event.title);
            }
        });

        browser.addOpenWindowListener(new OpenWindowListener() {

            @Override
            public void open(WindowEvent event) {
                if (!event.required)
                    return;
                IWorkbenchPage page = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage();
                try {
                    MozillaEditor editor = (MozillaEditor) page.openEditor(
                            new BrowserEditorInput(""
                                    + System.currentTimeMillis()),
                            MozillaEditor.ID);
                    event.browser = editor.getBrowser();
                } catch (PartInitException e) {
                    Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
                    IStatus status = new Status(IStatus.ERROR, bundle
                            .getSymbolicName(), bundle.getState(),
                            "Can not create browser editor part.", e);
                    Platform.getLog(bundle).log(status);
                }
            }
        });

        // 增加 call java from javascript 的功能。
        final BrowserFunction function = new JavaFunction(browser, "callJava",
                serviceRefs);

        browser.setUrl(getEditorInput().getName());
    }

    @Override
    public void setFocus() {
        // TODO Auto-generated method stub

    }

    public Browser getBrowser() {
        return browser;
    }

    public void setServiceRefs(Set<ServiceReference> serviceRefs) {
        this.serviceRefs = serviceRefs;
    }

}
