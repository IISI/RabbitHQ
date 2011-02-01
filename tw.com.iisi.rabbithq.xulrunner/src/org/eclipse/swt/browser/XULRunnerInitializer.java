package org.eclipse.swt.browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

/**
 * @author Chih-Liang Chang
 */
public class XULRunnerInitializer {

    static {
        Bundle bundle = Platform.getBundle("org.eclipse.swt");
        if (bundle != null) {
            URL resourceUrl = bundle.getResource("xulrunner");
            if (resourceUrl != null) {
                try {
                    URL fileUrl = FileLocator.toFileURL(resourceUrl);
                    File file = new File(fileUrl.toURI());
                    System.setProperty("org.eclipse.swt.browser.XULRunnerPath",
                            file.getAbsolutePath());
                } catch (IOException e) {
                    IStatus status = new Status(IStatus.ERROR,
                            bundle.getSymbolicName(), bundle.getState(),
                            "Xulrunner cannot be initialized.", e);
                    Platform.getLog(bundle).log(status);
                } catch (URISyntaxException e) {
                    IStatus status = new Status(IStatus.ERROR,
                            bundle.getSymbolicName(), bundle.getState(),
                            "Xulrunner cannot be initialized.", e);
                    Platform.getLog(bundle).log(status);
                }
            } else {
                FileNotFoundException e = new FileNotFoundException(
                        "Directory xulrnnner does not exist.");
                IStatus status = new Status(IStatus.WARNING,
                        bundle.getSymbolicName(), bundle.getState(),
                        "Xulrunner cannot be initialized.", e);
                Platform.getLog(bundle).log(status);
            }
        }
    }

}
