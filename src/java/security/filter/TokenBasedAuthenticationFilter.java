package security.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import model.pojo.Response;
import security.TokenBasedAuthentication;

public class TokenBasedAuthenticationFilter implements Filter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BODY = "<h1>401 NO AUTORIZADO</h1>";
    private final String CONTENT_TYPE_HEADER = "text/html; charset=UTF-8";
    private final String PREFIX = "Bearer ";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean isValidAuthentication = false;
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletResquest = (HttpServletRequest) servletRequest;
            String authorizationHeader = httpServletResquest.getHeader(AUTHORIZATION_HEADER);
            if (!authorizationHeader.isEmpty() && authorizationHeader.startsWith(PREFIX)) {
                String accessToken = authorizationHeader.replaceFirst(PREFIX, "");
                Response response = TokenBasedAuthentication.validateAccessToken(accessToken);
                if ( !response.isError()) {
                    isValidAuthentication = true;
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
            if (!isValidAuthentication) {
                if (servletResponse instanceof HttpServletResponse) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpServletResponse.addHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_HEADER);
                    httpServletResponse.getWriter().write(BODY);
                }
            }
        }
    }

    @Override
    public void destroy() {
    }

}