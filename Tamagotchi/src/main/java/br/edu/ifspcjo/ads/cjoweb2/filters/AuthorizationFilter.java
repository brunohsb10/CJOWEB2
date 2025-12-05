package br.edu.ifspcjo.ads.cjoweb2.filters;
import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// [cite: 81, 88, 99, 105] Atualizei as URLs para proteger as páginas de ANIMAIS
@WebFilter(urlPatterns = {"/homeServlet", "/animals", "/animals/*", "/adopters", "/adopters/*", "/home.jsp", "/adopter-list.jsp"}, filterName = "Authorization")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        
        // Verifica se a sessão é nula ou se o atributo "user" não existe
        if (session == null || session.getAttribute("user") == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            // Redireciona para o login caso não esteja autenticado
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        } else {
            // Se estiver logado, permite que a requisição continue para o Servlet/JSP
            chain.doFilter(request, response);
        }
    }
}