package br.edu.ifspcjo.ads.cjoweb2.servlets;

import java.io.IOException;
import java.time.LocalDate;

// Certifique-se de ter a biblioteca GSON no seu Build Path/Maven
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.edu.ifspcjo.ads.cjoweb2.dao.AnimalDao;
import br.edu.ifspcjo.ads.cjoweb2.model.User;
import br.edu.ifspcjo.ads.cjoweb2.utils.DataSourceSearcher;
import br.edu.ifspcjo.ads.cjoweb2.utils.LocalDateTypeAdapter; // Você precisará criar essa classe em utils se não tiver
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/animalStatistics")
public class AnimalStatisticsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String category = req.getParameter("category");

		HttpSession session = req.getSession(false);
		// O filtro já garante o user, mas é bom verificar
		if (session == null || session.getAttribute("user") == null) {
			return;
		}
		
		User user = (User) session.getAttribute("user");
		AnimalDao animalDao = new AnimalDao(DataSourceSearcher.getInstance().getDataSource());
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		if (category == null) return;

		switch (category) {
			case "bySpecies": // Adaptado de byType
				// Retorna a lista de AnimalBySpecies como JSON
				resp.getWriter().write(new Gson().toJson(animalDao.getAnimalsStatisticsBySpecies(user)));
				break;
				
			case "byBirthDate": // Adaptado de byDay
				// Usa o GsonBuilder com adaptador para lidar com LocalDate corretamente
				Gson gson = new GsonBuilder()
						.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
						.create();
				
				// Retorna a lista de AnimalByBirthDate como JSON
				resp.getWriter().write(gson.toJson(animalDao.getAnimalsStatisticsByBirthDate(user)));
				break;
		}
	}
}