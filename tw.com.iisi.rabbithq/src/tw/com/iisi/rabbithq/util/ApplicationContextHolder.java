package tw.com.iisi.rabbithq.util;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.context.ApplicationContext;

import tw.com.iisi.rabbithq.Activator;

public class ApplicationContextHolder {

    private static ApplicationContext appCtx;

    public static ApplicationContext getApplicationContext(String symbolicName) {
        if (appCtx == null) {
            BundleContext bundleContext = Platform.getBundle(symbolicName)
                    .getBundleContext();
            try {
                ServiceReference[] svcRef = bundleContext
                        .getAllServiceReferences(
                                ApplicationContext.class.getName(),
                                "(org.springframework.context.service.name="
                                        + symbolicName + ")");
                appCtx = (ApplicationContext) bundleContext
                        .getService(svcRef[0]);
            } catch (InvalidSyntaxException e) {
                IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                        "", e);
                Platform.getLog(Activator.getDefault().getBundle()).log(status);
            }
        }
        return appCtx;
    }

}
