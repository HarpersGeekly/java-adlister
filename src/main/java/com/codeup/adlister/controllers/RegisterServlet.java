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
        if (username !=null && password != null && email != null
                && !username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {

            // TODO: create a new user based off of the submitted information
            // a new user will have a 0 id..
            User user = new User(0, username, email, password);

            // TODO: if a user was successfully created, send them to their profile

            DaoFactory.getUsersDao().insert(user);
            //allow for automatic login:
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/profile");
        } else {
            doGet(request, response); //otherwise, show the register form again
        }
    }
}
