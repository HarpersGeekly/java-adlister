package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import com.codeup.adlister.util.Password;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("/profile");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // make sure we find a user with that username
        User user = DaoFactory.getUsersDao().findByUsername(username);
        // check the submitted password against what you have in your database (the hashedPassword from LoginServlet is now in DB, user.getPassword())
        boolean validAttempt = (user != null && Password.check(password, user.getPassword()));

        if (validAttempt) {
            // store the logged in user object in the session
            request.getSession().setAttribute("user", user); // changed ("user", username) to ("user", user);
            response.sendRedirect("/profile");
        } else {
            response.sendRedirect("/login");
        }


    }
}
