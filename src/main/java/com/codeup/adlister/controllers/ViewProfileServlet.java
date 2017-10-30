package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.ViewProfileServlet", urlPatterns = "/profile")
public class ViewProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }
        User user;

        // This is viewing someone else's profile:
        // If I have parameter in the query string ID, then query the database with a new method in the DAO,
        // and pass that user to the view
        if (request.getParameter("id") != null) {

            Long id = Long.parseLong(request.getParameter("id"));
            user  = DaoFactory.getUsersDao().findById(id);

        } else {
            // Otherwise take the user from the session
            user = (User) request.getSession().getAttribute("user");
        }
        // the jsp will always look for the session "user" first, so we change it to "profile"
        // so it can differentiate between the session user and a profile who's user isn't the session user.
        request.setAttribute("profile", user);
        //show the Users ads...
        request.setAttribute("ads", DaoFactory.getAdsDao().showUsersAds(user.getId()));
        // display the view
        request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    }
}