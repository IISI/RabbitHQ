package tw.com.iisi.rabbithq.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.restlet.data.MediaType;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * @author Chih-Liang Chang
 */
public class HtmlResource extends ServerResource {

    private String page;

    @Override
    protected void doInit() throws ResourceException {
        page = (String) getRequestAttributes().get("page");
    }

    @Get
    public Representation getHtml() throws IOException, URISyntaxException {
        URL url = Platform.getBundle("tw.com.iisi.rabbithq.web").getResource(
                "/WEB-INF/html/" + page);
        if (url != null) {
            URL fileUrl = FileLocator.toFileURL(url);
            return new FileRepresentation(new File(fileUrl.toURI()),
                    MediaType.TEXT_HTML);
        }
        throw new FileNotFoundException("Can not find file: " + page);
    }

}
