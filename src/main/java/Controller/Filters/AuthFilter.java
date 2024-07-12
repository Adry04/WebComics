package Controller.Filters;

import Model.TokenUtil;
import Model.User;
import Model.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter extends HttpFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Cookie[] cookies = httpRequest.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authToken")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        System.out.println("TOKEN: " + token);
        if (token != null && TokenUtil.validateToken(token) && session.getAttribute("userId") == null) {
            String email = TokenUtil.getEmailFromToken(token);
            System.out.println("EMAIL: " + email);
            User user = UserDAO.getUserFromEmail(email);
            session.setAttribute("email", email);
            session.setAttribute("nome",user.getFirstName());
            session.setAttribute("cognome", user.getLastName());
            session.setAttribute("isAdmin", user.getIsAdmin());
            session.setAttribute("userId", user.getId());
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}