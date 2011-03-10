package tw.com.iisi.rabbithq.views;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;

import tw.com.iisi.rabbithq.Activator;
import tw.com.iisi.rabbithq.util.ServerInfo;

/**
 * @author Chih-Liang Chang
 */
public class WelcomeView extends ViewPart {

    public static final String ID = "tw.com.iisi.rabbithq.views.WelcomeView";

    @Override
    public void createPartControl(Composite parent) {
        Browser browser;
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

        int port = ServerInfo.getInstance().getHttpPort();
        browser.setUrl("http://127.0.0.1:" + port
                + "/rabbithq/static/welcome.html");
    }

    @Override
    public void setFocus() {
        // TODO Auto-generated method stub

    }

}
