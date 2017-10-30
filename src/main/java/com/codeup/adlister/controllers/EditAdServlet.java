package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by RyanHarper on 10/27/17.
 */
@WebServlet(name = "controllers.EditAdServlet", urlPatterns = "/ads/edit")
public class EditAdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }

        if (request.getParameter("id") != null) {

            // get the id from the url, given to us on the Edit button on profile.jsp <a href="ads/edit?id=${ad.id}">
            Long id = Long.parseLong(request.getParameter("id"));
            // find the ad in the ads table with that id...
            DaoFactory.getAdsDao().findById(id);

            //show that ad in the view.
            Ad ad = DaoFactory.getAdsDao().findById(id);
            //set the attribute used in the view:
            request.setAttribute("ad", ad);
            // The edit.jsp form pre-populates with: "value=${ad.title}" value="${ad.description}, and HIDDEN ${ad.id}

            // show the .jsp
            request.getRequestDispatcher("/WEB-INF/ads/edit.jsp")
                    .forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Difference between CreateAd and UpdateAd is that we already have a user and ad in the database...
        // We don't need to create a new Ad. We get the parameters from our form's populated fields from the database, shown above in doGet()

        // No need: User user = (User)request.getSession().getAttribute("user");

        Ad ad = DaoFactory.getAdsDao().findById(Long.parseLong(request.getParameter("id")));
        ad.setTitle(request.getParameter("title"));
        ad.setDescription(request.getParameter("description"));

        /* No need:
            Ad ad = new Ad(
                user.getId(),
                request.getParameter("title"),
                request.getParameter("description")
        );*/

        DaoFactory.getAdsDao().update(ad);
        response.sendRedirect("/profile");
    }
}
