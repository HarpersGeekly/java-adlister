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

@WebServlet(name = "controllers.RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // show the registration form for this servlet (there's other servlets using this form too) EditUserServlet, and DeleteServlet
        request.setAttribute("action", "/register");
        request.getRequestDispatcher("/WEB-INF/users/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String passwordConfirmation = request.getParameter("confirm_password");

//        String hashedPassword = Password.hash(password);

        // ensure the submitted information is valid
        boolean inputHasErrors = username.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || (!password.equals(passwordConfirmation));

        if (inputHasErrors) {
            doGet(request, response); // show the register form again

        } else {
            // check if there's a user with that username already registered in the database:
            User existingUser = DaoFactory.getUsersDao().findByUsername(username);
            if(existingUser != null) {
                request.getSession().setAttribute("user", existingUser);
                response.sendRedirect("/register");
                return;
            }
            // create a new user based off of the submitted information:
//                  (If I used the other User constructor with long id in it,
//                  then i'd make the id parameter "0" below...kinda weird

//                    User user = new User(0, username, email, password);
//                         and set the id for a newly inserted user:
//                    Long id = DaoFactory.getUsersDao().insert(user);
//                    user.setId(id);
//
            // But, I'm going to use the other constructor with only username, email, and password in it.
            // When it's inserted in the database, the id auto increments.
            User user = new User(username, email, password);
            Long id = DaoFactory.getUsersDao().insert(user);
            user.setId(id);
            // If a user was successfully created, send them to their profile:
            // start a new session for the newly registered user
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/profile");
        }
    }
}
