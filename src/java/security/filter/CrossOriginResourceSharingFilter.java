package security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

public class CrossOriginResourceSharingFilter implements Filter {

    private final String ACCESS_CONTROL_ALLOW_HEADERS_HEADER = "Access-Control-Allow-Headers";
    private final String ACCESS_CONTROL_ALLOW_METHODS_HEADER = "Access-Control-Allow-Methods";
    private final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    private final String BODY = "<h1>401 NO AUTORIZADO - ORIGEN NO VÁLIDO</h1>";
    private final String CONTENT_TYPE_HEADER = "text/html; charset=UTF-8";
    private final String METHODS = "GET, OPTIONS, HEAD, PUT, POST";
    private final String ORIGIN_HEADER = "Origin";
    public final String[] ORIGINS = {"https://midominio.com", "htttps://misegundodominio.com.mx"};

    public CrossOriginResourceSharingFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            Set<String> allowedOrigins = new HashSet<>(Arrays.asList(ORIGINS));
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String originHeader = httpServletRequest.getHeader(ORIGIN_HEADER);
            if (allowedOrigins.contains(originHeader)) {
                httpServletResponse.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, originHeader);
                httpServletResponse.addHeader(ACCESS_CONTROL_ALLOW_METHODS_HEADER, METHODS);
                httpServletResponse.addHeader(ACCESS_CONTROL_ALLOW_HEADERS_HEADER, "*");
                if (httpServletRequest.getMethod().equals("OPTIONS")) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
                }
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.addHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_HEADER);
                httpServletResponse.getWriter().write(BODY);
            }
        }
    }

    @Override
    public void destroy() {
    }

}