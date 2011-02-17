package tw.com.iisi.rabbithq.editors;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.osgi.framework.Bundle;

import tw.com.iisi.rabbithq.Activator;

public class JavaFunction extends BrowserFunction {

    public JavaFunction(Browser browser, String name) {
        super(browser, name);
    }

    @Override
    public Object function(Object[] arguments) {
        String symbolicName = (String) arguments[0];
        String version = arguments[1] == null ? null : (String) arguments[1];
        String className = (String) arguments[2];
        Bundle[] bundles = Platform.getBundles(symbolicName, version);
        IStatus status = null;
        try {
            if (bundles != null && bundles.length > 0) {
                Bundle bundle = bundles[0];
                Class clazz = bundle.loadClass(className);
                if (IJavaHandler.class.isAssignableFrom(clazz)) {
                    IJavaHandler handler = (IJavaHandler) clazz.newInstance();
                    return handler.execute(getBrowser(), arguments);
                } else {
                    status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                            "Class [" + className
                                    + "] is not type of IJavaHandler.");
                }
            } else {
                status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                        "Bundle [" + symbolicName + "] not found.");
            }
        } catch (ClassNotFoundException e) {
            status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                    "Loading class failed.", e);
        } catch (InstantiationException e) {
            status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                    "Failed to create object.", e);
        } catch (IllegalAccessException e) {
            status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                    "Failed to create object.", e);
        }
        ILog logger = Platform.getLog(Activator.getDefault().getBundle());
        logger.log(status);
        return super.function(arguments);
    }

}
