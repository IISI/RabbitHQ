package tw.com.iisi.rabbithq.editors;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.springframework.context.ApplicationContext;

import tw.com.iisi.rabbithq.Activator;
import tw.com.iisi.rabbithq.util.ApplicationContextHolder;

public class JavaFunction extends BrowserFunction {

    public JavaFunction(Browser browser, String name) {
        super(browser, name);
    }

    @Override
    public Object function(Object[] arguments) {
        Object result = null;
        ApplicationContext appCtx = ApplicationContextHolder
                .getApplicationContext(Activator.PLUGIN_ID);
        Set<IJavaHandler> handlers = (Set<IJavaHandler>) appCtx
                .getBean("javaHandlers");
        for (Iterator<IJavaHandler> iter = handlers.iterator(); iter.hasNext();) {
            IJavaHandler handler = iter.next();
            if (handler.getName().equals(arguments[0])) {
                result = handler.execute(getBrowser(), arguments);
                break;
            }
        }
        return result;
    }

}
