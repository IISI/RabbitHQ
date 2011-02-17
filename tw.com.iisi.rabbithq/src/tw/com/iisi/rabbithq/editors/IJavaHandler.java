package tw.com.iisi.rabbithq.editors;

import org.eclipse.swt.browser.Browser;

public interface IJavaHandler {

    Object execute(Browser browser, Object[] args);

    String getName();

}
