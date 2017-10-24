package com.codeup.adlister.controllers;
import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: show the registration form
        request.getRequestDispatcher("/WEB-INF/users/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // TODO: ensure the submitted information is valid
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            doGet(request, response); // show the register form again
        } else {

            User existingUser = DaoFactory.getUsersDao().findByUsername(username);
            if(existingUser != null) {
                response.sendRedirect("/register");
                return;
            }

            // TODO: create a new user based off of the submitted information
            // a new user will have a 0 id..
            User user = new User(0, username, email, password);
            // but we can set the id now for a newly inserted user.
            Long id = DaoFactory.getUsersDao().insert(user);
            user.setId(id);
            // TODO: if a user was successfully created, send them to their profile
            //start a new session for the newly registered user
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/profile");
        }
    }
}
