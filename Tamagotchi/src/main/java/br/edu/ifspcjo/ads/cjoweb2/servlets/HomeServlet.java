package br.edu.ifspcjo.ads.cjoweb2.servlets; //  Atenção ao pacote correto do seu projeto

import java.io.IOException;
import java.util.List;

import br.edu.ifspcjo.ads.cjoweb2.dao.AnimalDao; //  Alterado de ActivityDao
import br.edu.ifspcjo.ads.cjoweb2.model.Animal; // [cite: 98] Alterado de Activity
import br.edu.ifspcjo.ads.cjoweb2.model.User;
import br.edu.ifspcjo.ads.cjoweb2.utils.DataSourceSearcher;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/homeServlet")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public HomeServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// O Filtro (AuthorizationFilter) já garantiu que o usuário está logado.
		// Podemos pegar a sessão diretamente.
		HttpSession session = req.getSession(false);
		User user = (User)session.getAttribute("user");
		
		
		AnimalDao animalDao = new AnimalDao(DataSourceSearcher.getInstance().getDataSource());
		List<Animal> userAnimals = animalDao.getAnimalsByUser(user); // [cite: 104] Busca animais, não atividades
		
		// Define o atributo para o JSP
		req.setAttribute("userAnimals", userAnimals); 
		
		req.setAttribute("name", user.getName()); 

		RequestDispatcher dispatcher = req.getRequestDispatcher("/home.jsp");
		dispatcher.forward(req, resp);
	}
}