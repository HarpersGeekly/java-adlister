package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.ShowAdServlet", urlPatterns = "/show")
public class ShowAdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //starts with the index.jsp
        // we're getting the id to show...
        Long id = Long.parseLong(request.getParameter("id"));//...from the query string in the url
        // use the MySQLAdsDao and call the method showOneAd() that takes that id as an argument:
        request.setAttribute("ad", DaoFactory.getAdsDao().showOneAd(id));
        request.getRequestDispatcher("/WEB-INF/ads/show.jsp").forward(request, response);
    }
}
