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
		// Redireciona GET para POST para manter a lógica unificada, 
        // caso o form use method="get" ou "post"
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Captura e trata os parâmetros de Filtro
		
		// Espécie (String)
		String species = req.getParameter("species");
		if (species != null && species.isEmpty()) {
			species = null;
		}

		// Status (String)
		String status = req.getParameter("status");
		if (status != null && status.isEmpty()) {
			status = null;
		}

		// Data Inicial
		String initialDateStr = req.getParameter("initial-date");
		LocalDate initialDate = null;
		if (initialDateStr != null && !initialDateStr.isEmpty()) {
			initialDate = LocalDate.parse(initialDateStr);
		}

		// Data Final
		String finalDateStr = req.getParameter("final-date");
		LocalDate finalDate = null;
		if (finalDateStr != null && !finalDateStr.isEmpty()) {
			finalDate = LocalDate.parse(finalDateStr);
		}
		
		// 2. Recupera o Usuário da sessão
		// (O AuthorizationFilter já garantiu que ele está logado)
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		
		// 3. Configura o Objeto Filtro
		AnimalFilter filter = new AnimalFilter();
		filter.setUser(user);
		filter.setSpecies(species);
		filter.setStatus(status);
		filter.setInitialDate(initialDate);
		filter.setFinalDate(finalDate);
		
		// 4. Busca no DAO
		AnimalDao animalDao = new AnimalDao(DataSourceSearcher.getInstance().getDataSource());
		List<Animal> userAnimals = animalDao.getAnimalsByFilter(filter);
		
		// 5. Envia para o JSP
		// O nome do atributo DEVE ser "userAnimals" para funcionar com seu home.jsp [cite: 104, 171]
		req.setAttribute("userAnimals", userAnimals);
		
		// Retorna para a home com os resultados filtrados
		RequestDispatcher dispatcher = req.getRequestDispatcher("/home.jsp");
		dispatcher.forward(req, resp);
	}
	
}