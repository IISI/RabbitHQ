package tw.com.iisi.aquarius.web;

import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.request.target.basic.StringRequestTarget;

public abstract class AbstractAquariusPage extends WebPage {

    final AbstractAjaxBehavior behave = new AbstractAjaxBehavior() {

        @Override
        public void renderHead(IHeaderResponse response) {
            response.renderJavascript("var __ajaxHandler = '"
                    + getCallbackUrl(false) + "';", "" + System.nanoTime());
            super.renderHead(response);
        }

        @Override
        public void onRequest() {
            Map<String, String[]> parameterMap = ((WebRequestCycle) RequestCycle
                    .get()).getRequest().getParameterMap();
            PageParameters params = new PageParameters(parameterMap);
            String result = AbstractAquariusPage.this.handleAjaxRequest(params);
            StringRequestTarget target = new StringRequestTarget(
                    result == null ? "" : result);
            RequestCycle.get().setRequestTarget(target);
        }
    };

    public AbstractAquariusPage() {
        super();
        execute();
    }

    public AbstractAquariusPage(PageParameters parameters) {
        super(parameters);
        execute();
    }

    private void execute() {
        add(behave);
    }

    public String handleAjaxRequest(PageParameters params) {
        return "";
    }

}
