//Controllo sui token
package Controller.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "HttpsControlFilter", urlPatterns = "/*")
public class HttpsFilter extends HttpFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean isSecure = request.isSecure();
        if (!isSecure) {
            HttpServletRequest requestServlet = (HttpServletRequest) request;
            String serverName = requestServlet.getServerName();
            int serverPort = 8443;
            String contextPath = requestServlet.getContextPath(); // Aggiunge il contesto dell'applicazione

            String redirectUrl = "https://" + serverName + ":" + serverPort + contextPath + "/";
            ((HttpServletResponse) response).sendRedirect(redirectUrl);
            return;
        }
        chain.doFilter(request, response);
    }
}