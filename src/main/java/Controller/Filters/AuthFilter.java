package Controller.Filters;

import Model.*;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter extends HttpFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Cookie[] cookies = httpRequest.getCookies();
        String token = null;
        List<String> cartParts = new ArrayList<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authToken")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token != null && TokenUtil.validateToken(token) && session.getAttribute("userId") == null) {
            String email = TokenUtil.getEmailFromToken(token);
            User user = UserDAO.getUserFromEmail(email);
            session.setAttribute("email", email);
            session.setAttribute("nome", user.getFirstName());
            session.setAttribute("cognome", user.getLastName());
            session.setAttribute("isAdmin", user.getIsAdmin());
            session.setAttribute("userId", user.getId());
            session.setAttribute("cart", CartDAO.getCart(user.getId()));
            chain.doFilter(request, response);
        }  else if (token == null && session.getAttribute("userId") != null) {
            session.setAttribute("email", null);
            session.setAttribute("nome", null);
            session.setAttribute("cognome", null);
            session.setAttribute("isAdmin", null);
            session.setAttribute("userId", null);
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}