package br.edu.ifspcjo.ads.cjoweb2.servlets;

import java.io.IOException;
import br.edu.ifspcjo.ads.cjoweb2.dao.AdopterDao;
import br.edu.ifspcjo.ads.cjoweb2.model.Adopter;
import br.edu.ifspcjo.ads.cjoweb2.utils.DataSourceSearcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/adopters", "/adopters/register"})
public class AdopterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/adopters".equals(path)) {
            AdopterDao dao = new AdopterDao(DataSourceSearcher.getInstance().getDataSource());
            req.setAttribute("adopters", dao.findAll());
            req.getRequestDispatcher("/adopter-list.jsp").forward(req, resp);
        } else if ("/adopters/register".equals(path)) {
            req.getRequestDispatcher("/adopter-register.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        
        Adopter a = new Adopter(null, name, email, phone, address);
        new AdopterDao(DataSourceSearcher.getInstance().getDataSource()).save(a);
        
        resp.sendRedirect(req.getContextPath() + "/adopters?success=Cadastrado");
    }
}