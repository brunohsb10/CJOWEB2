package br.edu.ifspcjo.ads.cjoweb2.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import br.edu.ifspcjo.ads.cjoweb2.dao.AnimalDao;
import br.edu.ifspcjo.ads.cjoweb2.filters.AnimalFilter;
import br.edu.ifspcjo.ads.cjoweb2.model.Animal;
import br.edu.ifspcjo.ads.cjoweb2.model.User;
import br.edu.ifspcjo.ads.cjoweb2.utils.DataSourceSearcher;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/animalSearchServlet")
public class AnimalSearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AnimalSearchServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		String species = req.getParameter("species");
		if (species != null && species.isEmpty()) {
			species = null;
		}

		
		String status = req.getParameter("status");
		if (status != null && status.isEmpty()) {
			status = null;
		}

		
		String initialDateStr = req.getParameter("initial-date");
		LocalDate initialDate = null;
		if (initialDateStr != null && !initialDateStr.isEmpty()) {
			initialDate = LocalDate.parse(initialDateStr);
		}

		
		String finalDateStr = req.getParameter("final-date");
		LocalDate finalDate = null;
		if (finalDateStr != null && !finalDateStr.isEmpty()) {
			finalDate = LocalDate.parse(finalDateStr);
		}
		
		
		
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		
		
		AnimalFilter filter = new AnimalFilter();
		filter.setUser(user);
		filter.setSpecies(species);
		filter.setStatus(status);
		filter.setInitialDate(initialDate);
		filter.setFinalDate(finalDate);
		
		
		AnimalDao animalDao = new AnimalDao(DataSourceSearcher.getInstance().getDataSource());
		List<Animal> userAnimals = animalDao.getAnimalsByFilter(filter);
		
		
		req.setAttribute("userAnimals", userAnimals);
		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/home.jsp");
		dispatcher.forward(req, resp);
	}
	
}
