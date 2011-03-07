package tw.com.iisi.rabbithq.web;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.routing.Router;

/**
 * @author Chih-Liang Chang
 */
public class RestApp extends Application {

    @Override
    public Restlet createInboundRoot() {

        Restlet rcp = new Restlet(getContext()) {

            @Override
            public void handle(Request request, Response response) {
                final String id = (String) request.getAttributes().get("id");
                Display.getDefault().asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            IWorkbench workbench = PlatformUI.getWorkbench();
                            IWorkbenchWindow window = workbench
                                    .getActiveWorkbenchWindow();
                            workbench.showPerspective(id, window);
                        } catch (WorkbenchException e) {
                            e.printStackTrace();
                        }
                    }
                });
                response.setStatus(Status.SUCCESS_NO_CONTENT);
            }
        };

        Router router = new Router(getContext());
        router.attach("/rcp/{id}", rcp);
        return router;
    }

}
