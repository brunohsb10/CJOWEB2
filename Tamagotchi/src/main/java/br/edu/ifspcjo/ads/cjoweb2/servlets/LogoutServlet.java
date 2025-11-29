package br.edu.ifspcjo.ads.cjoweb2.servlets;


import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logoutServlet") // Mudei para /logoutServlet para ser consistente (opcional)
public class LogoutServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false); // Pega a sessão sem criar uma nova
		
        if (session != null) {
            session.invalidate(); // Destrói a sessão (faz o logout)
        }
		
        // Envia o usuário de volta para a tela de login
		RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
		dispatcher.forward(req, resp);
	}
}