package Controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "/HttpsControlFilter", urlPatterns = "/*")
public class HttpsFilter extends HttpFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean isSecure = request.isSecure();
        if (!isSecure) { //TODO va tolto il negato
            chain.doFilter(request, response);
        } else {
            HttpServletRequest requestServlet = (HttpServletRequest) request;
            String redirectUrl = "https://" + requestServlet.getServerName() + requestServlet.getRequestURI();
            ((HttpServletResponse) response).sendRedirect(redirectUrl);
        }
    }
}