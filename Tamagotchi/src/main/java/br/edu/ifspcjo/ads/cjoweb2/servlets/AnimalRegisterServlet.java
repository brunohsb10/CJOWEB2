package br.edu.ifspcjo.ads.cjoweb2.servlets;

import java.io.IOException;
import java.time.LocalDate;

import br.edu.ifspcjo.ads.cjoweb2.dao.AnimalDao;
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

@WebServlet("/animalRegisterServlet")
public class AnimalRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AnimalRegisterServlet() {
		super();
	}

	// POST: Salvar e Editar (Redireciona a página normalmente)
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String idParam = req.getParameter("id");
			Long id = (idParam != null && !idParam.isEmpty()) ? Long.parseLong(idParam) : 0L;
			
			String name = req.getParameter("name");
			String species = req.getParameter("species");
			LocalDate dateOfBirth = LocalDate.parse(req.getParameter("birth_date"));
			String description = req.getParameter("description");
			String status = req.getParameter("status");
	
			HttpSession session = req.getSession(false);
			User user = (User) session.getAttribute("user");
	
			AnimalDao animalDao = new AnimalDao(DataSourceSearcher.getInstance().getDataSource());
			Animal animal = new Animal();
			animal.setName(name);
			animal.setSpecies(species);
			animal.setDateOfBirth(dateOfBirth);
			animal.setDescription(description);
			animal.setStatus(status);
			animal.setUserId(user.getId());
	
			if (id == 0) {
				animalDao.save(animal);
			} else {
				animal.setId(id);
				animalDao.update(animal);
			}
	
			req.setAttribute("result", "registered");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "error");
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/animal-register.jsp");
		dispatcher.forward(req, resp);
	}

	// GET: Excluir (JSON) e Carregar para Edição (HTML)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		String animalIdParam = req.getParameter("animal-id");
		
		if (action != null && animalIdParam != null && !animalIdParam.isEmpty()) {
			try {
				Long animalId = Long.parseLong(animalIdParam);
				AnimalDao animalDao = new AnimalDao(DataSourceSearcher.getInstance().getDataSource());
				
				// --- AÇÃO 1: EXCLUIR (AJAX) ---
				// Esta parte é crítica para o botão da lixeira funcionar
				if (action.equals("delete")) {
					animalDao.delete(animalId);
					
					// Responde JSON manual
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write("true");
					
					return; // IMPORTANTE: Pára o código aqui! Não carrega HTML.
				}
				
				// --- AÇÃO 2: CARREGAR PARA EDIÇÃO ---
				else if (action.equals("update")) {
					Animal animal = animalDao.getAnimalById(animalId);
					if (animal != null) {
						req.setAttribute("animal", animal);
						RequestDispatcher dispatcher = req.getRequestDispatcher("/animal-register.jsp");
						dispatcher.forward(req, resp);
						return;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace(); // Veja o erro no console do Eclipse se falhar
			}
		}

		// Se não for delete nem update, volta para a home
		RequestDispatcher dispatcher = req.getRequestDispatcher("/homeServlet");
		dispatcher.forward(req, resp);
	}
}