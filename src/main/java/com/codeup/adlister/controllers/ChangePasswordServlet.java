package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by RyanHarper on 1/11/18.
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = "/profile/editPassword")
public class ChangePasswordServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
        }

        //get user id from parameter.
        Long id = Long.parseLong(request.getParameter("id"));

        //find the user and store them into a User object
        User user = DaoFactory.getUsersDao().findById(id);
        request.setAttribute("user", user);
        // show the .jsp
        request.getRequestDispatcher("/WEB-INF/users/editPassword.jsp").forward(request, response);


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("confirm_password");

        boolean passwordsNotMatch = !password.equals(passwordConfirmation);
        boolean passwordEmpty = password.isEmpty();
        boolean confirmPasswordEmpty = passwordConfirmation.isEmpty();

        if (passwordEmpty && confirmPasswordEmpty) {
            //"Both password fields need to be filled out"
            request.setAttribute("passwordEmpty", passwordEmpty);
            request.setAttribute("passwordEmpty", confirmPasswordEmpty);
            doGet(request, response);
            return;
        }

        if (!passwordEmpty && confirmPasswordEmpty) {
            //"Both password fields need to be filled out"
            request.setAttribute("passwordEmpty", confirmPasswordEmpty);
            doGet(request, response);
            return;
        }

        if (passwordEmpty && !confirmPasswordEmpty) {
            //"Both password fields need to be filled out"
            request.setAttribute("passwordEmpty", passwordEmpty);
            doGet(request, response);
            return;
        }

        if (passwordsNotMatch && (!passwordEmpty && !confirmPasswordEmpty)) {
            // "Passwords don't match"
            request.setAttribute("passwordsNotMatch", passwordsNotMatch);
            doGet(request, response);
            return;
        }


        // Update the user in the database with the newly entered parameters:
            User user = DaoFactory.getUsersDao().findById(id);

            user.setPassword(password); // this will hash it too. Check out the model :)
            DaoFactory.getUsersDao().updatePassword(user);
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/profile");
        }
    }
