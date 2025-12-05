package br.edu.ifspcjo.ads.cjoweb2.servlets;

import java.io.IOException;
import java.time.LocalDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import br.edu.ifspcjo.ads.cjoweb2.dao.AnimalDao;
import br.edu.ifspcjo.ads.cjoweb2.utils.DataSourceSearcher;
import br.edu.ifspcjo.ads.cjoweb2.utils.LocalDateTypeAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/animalStatistics")
public class AnimalStatisticsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = req.getParameter("category");
        
        AnimalDao animalDao = new AnimalDao(DataSourceSearcher.getInstance().getDataSource());
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (category == null) return;

        switch (category) {
            case "bySpecies":
                
                resp.getWriter().write(new Gson().toJson(animalDao.getAnimalsStatisticsBySpecies()));
                break;
                
            case "byBirthDate":
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                        .create();
               
                resp.getWriter().write(gson.toJson(animalDao.getAnimalsStatisticsByBirthDate()));
                break;
        }
    }
}
