package br.edu.ifspcjo.ads.cjoweb2.servlets;

import java.io.IOException;
import java.time.LocalDate;
import br.edu.ifspcjo.ads.cjoweb2.dao.AdopterDao;
import br.edu.ifspcjo.ads.cjoweb2.dao.AnimalDao;
import br.edu.ifspcjo.ads.cjoweb2.model.Animal;
import br.edu.ifspcjo.ads.cjoweb2.model.User;
import br.edu.ifspcjo.ads.cjoweb2.utils.DataSourceSearcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/animals", "/animals/register", "/animals/delete", "/animals/adopt"})
public class AnimalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        AnimalDao dao = new AnimalDao(DataSourceSearcher.getInstance().getDataSource());

        if ("/animals".equals(path)) {
            req.setAttribute("userAnimals", dao.findAll());
            req.getRequestDispatcher("/home.jsp").forward(req, resp);
        } else if ("/animals/register".equals(path)) {
            String id = req.getParameter("id");
            if(id != null) req.setAttribute("animal", dao.getAnimalById(Long.parseLong(id)));
            req.getRequestDispatcher("/animal-register.jsp").forward(req, resp);
        } else if ("/animals/delete".equals(path)) {
            dao.delete(Long.parseLong(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/animals");
        } else if ("/animals/adopt".equals(path)) {
            req.setAttribute("animal", dao.getAnimalById(Long.parseLong(req.getParameter("id"))));
            req.setAttribute("adopters", new AdopterDao(DataSourceSearcher.getInstance().getDataSource()).findAll());
            req.getRequestDispatcher("/animal-adopt.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        AnimalDao dao = new AnimalDao(DataSourceSearcher.getInstance().getDataSource());

        if ("/animals/register".equals(path)) {
            Animal a = new Animal();
            String idParam = req.getParameter("id");
            a.setName(req.getParameter("name"));
            a.setSpecies(req.getParameter("species"));
            a.setDateOfBirth(LocalDate.parse(req.getParameter("birth_date")));
            a.setDescription(req.getParameter("description"));
            a.setStatus(req.getParameter("status"));
            a.setUserId(((User)req.getSession().getAttribute("user")).getId());

            if(idParam == null || idParam.isEmpty() || idParam.equals("0")) dao.save(a);
            else { a.setId(Long.parseLong(idParam)); dao.update(a); }
            resp.sendRedirect(req.getContextPath() + "/animals");
        } else if ("/animals/adopt".equals(path)) {
            Long animalId = Long.parseLong(req.getParameter("animalId"));
            Integer adopterId = Integer.parseInt(req.getParameter("adopterId"));
            dao.completeAdoption(animalId, adopterId);
            resp.sendRedirect(req.getContextPath() + "/animals");
        }
    }
}