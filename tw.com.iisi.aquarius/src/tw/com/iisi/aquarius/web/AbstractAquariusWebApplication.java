package tw.com.iisi.aquarius.web;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public abstract class AbstractAquariusWebApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        addComponentInstantiationListener(new SpringComponentInjector(this));
        getMarkupSettings().setStripWicketTags(true);
        getMarkupSettings().setCompressWhitespace(true);
        getPageSettings().setAutomaticMultiWindowSupport(false);
    }

}
