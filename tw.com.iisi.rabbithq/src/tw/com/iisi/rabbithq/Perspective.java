package tw.com.iisi.rabbithq;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

    /**
     * The ID of the perspective as specified in the extension.
     */
    public static final String ID = "tw.com.iisi.rabbithq.perspective";

    public void createInitialLayout(IPageLayout layout) {
    }
}
