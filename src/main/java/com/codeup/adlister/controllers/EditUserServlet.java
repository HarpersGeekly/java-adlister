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

/**
 * Created by RyanHarper on 11/1/17.
 */
@WebServlet(name = "EditUserServlet", urlPatterns = "/profile/edit")
public class EditUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }

        // if there's a user id in the url parameter:
        if (request.getParameter("id") != null) {
            // get the id from the url, given to us on the Edit button on profile.jsp
            // <a href="/register?id=${user.id}">edit profile</a>
            Long id = Long.parseLong(request.getParameter("id"));
            // find the user in the users table with that id...
            DaoFactory.getUsersDao().findById(id);
            //show that user in the view and set the attribute to be used in the view.
            User user = DaoFactory.getUsersDao().findById(id);
            request.setAttribute("user", user);

            request.setAttribute("action", "/profile/edit");
            request.getRequestDispatcher("/WEB-INF/users/register.jsp")
                    .forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // I want three main things when I edit a user:
        // 1) Hash their newly edited password
        // 2) Check if a username already exists
        // 3) Check if their new passwords match

        Long id = Long.parseLong(request.getParameter("id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("confirm_password");
        String hashedPassword = Password.hash(password);

        // check if the passwords match:
        boolean inputHasErrors = !password.equals(passwordConfirmation);
        if (inputHasErrors) {
            System.out.println("password error");
            request.getRequestDispatcher("/WEB-INF/users/register.jsp") // show the register form again
                    .forward(request, response); //FORM VALIDATION will go here eventually...
        }

        // after a user submits the form...
        // check if a username already exists in the database for the newly edited name.
        // But also handle the issue of when a user doesn't change their name.

        User existingUser = DaoFactory.getUsersDao().findById(id); // get the existing user by id...
        if (!existingUser.getUsername().equals(username)) { // if the username on the pre-populated form doesn't equal the user found in the database
            User updatedUser = DaoFactory.getUsersDao().findByUsername(username); // then that must be an updated name, so check if that updated name is in the database.
            if (updatedUser != null) { // if there's someone by that username already in the database...
                System.out.println("existing user error");
                request.getRequestDispatcher("/WEB-INF/users/register.jsp") // show the register form again
                        .forward(request, response);
                return;
            }
        }

        // Update the user in the database with the newly entered parameters:
        User user = DaoFactory.getUsersDao().findById(Long.parseLong(request.getParameter("id")));

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);

        DaoFactory.getUsersDao().updateUser(user);
        response.sendRedirect("/profile");
    }
}