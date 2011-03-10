package tw.com.iisi.rabbithq;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import tw.com.iisi.rabbithq.views.WelcomeView;

public class Perspective implements IPerspectiveFactory {

    /**
     * The ID of the perspective as specified in the extension.
     */
    public static final String ID = "tw.com.iisi.rabbithq.perspective";

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);
        layout.setFixed(true);

        layout.addStandaloneView(WelcomeView.ID, false, IPageLayout.LEFT,
                0.95f, editorArea);
        layout.getViewLayout(WelcomeView.ID).setCloseable(false);
    }
}
