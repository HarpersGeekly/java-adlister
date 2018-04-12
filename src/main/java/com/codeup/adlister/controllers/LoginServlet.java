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
        request.getRequestDispatcher("/WEB-INF/users/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        request.setAttribute("username", username);  //sticky form value
        request.setAttribute("password", password);  //sticky form value

        User user = DaoFactory.getUsersDao().findByUsername(username); // make sure we find a user with that username

        boolean userExists = (user != null);
        boolean validAttempt = (user != null && Password.check(password, user.getPassword()));
        boolean usernameEmpty = username.isEmpty();
        boolean passwordEmpty = password.isEmpty();

        if (userExists && !passwordEmpty) {
            boolean passwordCorrect = Password.check(password, user.getPassword()); // check the submitted password against what I have in the database
            // incorrect password:
            if (!passwordCorrect) {
                request.setAttribute("passwordIncorrect", passwordCorrect);
                doGet(request, response); // show the register form again
            }
        }
        // both empty...
        if (usernameEmpty && passwordEmpty) {
            request.setAttribute("usernameEmpty", usernameEmpty);
            request.setAttribute("passwordEmpty", passwordEmpty);
            doGet(request, response); // show the register form again
            return;
        }

        //one or the other...
        if (!usernameEmpty && passwordEmpty) {
            request.setAttribute("passwordEmpty", passwordEmpty);
            doGet(request, response);
            return;
        }

        if (usernameEmpty && !passwordEmpty) {
            request.setAttribute("usernameEmpty", usernameEmpty);
            doGet(request, response);
            return;
        }

        // username doesn't exist:
        if ((!usernameEmpty && !passwordEmpty) && !userExists) {
            request.setAttribute("userNotExist", !userExists);
            doGet(request, response);
            return;
        }

        if (validAttempt ) {
            // store the logged in user object in the session
            request.getSession().setAttribute("user", user); // changed ("user", username) to ("user", user);
            response.sendRedirect("/profile");

        } else {
            request.getRequestDispatcher("/WEB-INF/users/login.jsp").forward(request, response);
        }
    }
}
