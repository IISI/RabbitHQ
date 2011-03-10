package tw.com.iisi.rabbithq.util;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import tw.com.iisi.rabbithq.Activator;

/**
 * @author Chih-Liang Chang
 */
public class ServerInfo {

    private static ServerInfo INSTANCE;

    private ServerInfo() {

    }

    public static ServerInfo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerInfo();
        }
        return INSTANCE;
    }

    private Connector[] getAllConnectors() {
        try {
            BundleContext context = Platform.getBundle(Activator.PLUGIN_ID)
                    .getBundleContext();
            ServiceReference[] refs = context.getAllServiceReferences(
                    Server.class.getName(),
                    "(managedServerName=defaultJettyServer)");
            for (ServiceReference ref : refs) {
                Server server = (Server) context.getService(ref);
                return server.getConnectors();
            }
        } catch (InvalidSyntaxException e) {
            IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                    "Can not find jetty server.", e);
            Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
            Platform.getLog(bundle).log(status);
            throw new RuntimeException("Can not find jetty server.", e);
        }
        throw new RuntimeException("Can not find jetty server.");
    }

    public int getHttpPort() {
        Connector[] conns = getAllConnectors();
        for (Connector conn : conns) {
            if (conn.getConfidentialPort() > 0) {
                return conn.getLocalPort();
            }
        }
        return 0;
    }

    public int getHttpsPort() {
        Connector[] conns = getAllConnectors();
        for (Connector conn : conns) {
            if (conn.getConfidentialPort() == 0) {
                return conn.getLocalPort();
            }
        }
        return 0;
    }

}
