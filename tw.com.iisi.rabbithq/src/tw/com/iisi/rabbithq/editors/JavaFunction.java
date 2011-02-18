package tw.com.iisi.rabbithq.editors;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.service.exporter.OsgiServicePropertiesResolver;
import org.springframework.osgi.service.importer.ServiceReferenceProxy;

/**
 * @author Chih-Liang Chang
 */
public class JavaFunction extends BrowserFunction {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private Set<ServiceReference> serviceRefs;

    public JavaFunction(Browser browser, String name,
            Set<ServiceReference> serviceRefs) {
        super(browser, name);
        this.serviceRefs = serviceRefs;
    }

    @Override
    public Object function(Object[] arguments) {
        Object result = null;
        boolean found = false;

        if (serviceRefs == null) {
            logger.warn("IJavaHandler service is empty.");
            return result;
        }

        for (Iterator<ServiceReference> iter = serviceRefs.iterator(); iter
                .hasNext();) {
            ServiceReference ref = iter.next();
            String beanName = (String) ref
                    .getProperty(OsgiServicePropertiesResolver.BEAN_NAME_PROPERTY_KEY);
            if (beanName != null && beanName.equals(arguments[0])) {
                ServiceReference nativeReference = ((ServiceReferenceProxy) ref)
                        .getTargetServiceReference();
                IJavaHandler handler = (IJavaHandler) nativeReference
                        .getBundle().getBundleContext()
                        .getService(nativeReference);
                result = handler.execute(getBrowser(), arguments);
                nativeReference.getBundle().getBundleContext()
                        .ungetService(nativeReference);
                found = true;
                break;
            }
        }

        if (!found) {
            logger.warn("Cannot find matching service for IJavaHandler.");
        }

        return result;
    }

}
