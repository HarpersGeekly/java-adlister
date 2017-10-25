package com.codeup.adlister.controllers;
import com.codeup.adlister.dao.DaoFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by RyanHarper on 10/25/17.
 */

@WebServlet(name = "controllers.SearchServlet", urlPatterns = "/search")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchQuery = request.getParameter("search");

        request.setAttribute("ads", DaoFactory.getAdsDao().search(searchQuery));

        request.getRequestDispatcher("/WEB-INF/ads/index.jsp").forward(request, response);
    }
}
