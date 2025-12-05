package br.edu.ifspcjo.ads.cjoweb2.servlets;


import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logoutServlet") 
public class LogoutServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false); 
		
        if (session != null) {
            session.invalidate(); 
        }
		
        
		RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
		dispatcher.forward(req, resp);
	}
}
