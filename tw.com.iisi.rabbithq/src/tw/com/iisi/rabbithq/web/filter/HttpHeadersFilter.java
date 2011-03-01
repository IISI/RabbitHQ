package tw.com.iisi.rabbithq.web.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Chih-Liang Chang
 */
public class HttpHeadersFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            boolean passed = true;
            for (Enumeration<String> e = filterConfig.getInitParameterNames(); e
                    .hasMoreElements();) {
                String name = e.nextElement();
                String value = filterConfig.getInitParameter(name);
                if (req.getHeader(name) == null
                        || !req.getHeader(name).contains(value)) {
                    passed = false;
                    break;
                }
            }
            if (passed) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response)
                        .setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
