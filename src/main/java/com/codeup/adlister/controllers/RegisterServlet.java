package com.codeup.adlister.controllers;
import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import com.codeup.adlister.util.Email;
import com.codeup.adlister.util.Password;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "controllers.RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // show the registration form for this servlet (there's other servlets using this form too) EditUserServlet, and DeleteServlet
        request.getRequestDispatcher("/WEB-INF/users/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String passwordConfirmation = request.getParameter("confirm_password");

//        String hashedPassword = Password.hash(password); Don't have to hash here. User model's constructor takes care of that with setPassword();

        // ensure the submitted information is valid
//        boolean inputHasErrors = username.isEmpty()
//                || email.isEmpty()
//                || password.isEmpty()
//                || (!password.equals(passwordConfirmation));
        boolean inputHasErrors = false;

        boolean usernameEmpty = username.isEmpty();
        boolean passwordEmpty = password.isEmpty();
        boolean emailEmpty = email.isEmpty();
        boolean passwordsNotMatch = !password.equals(passwordConfirmation);
        User userExists = DaoFactory.getUsersDao().findByUsername(username);

        ArrayList<String> listOfErrors = new ArrayList<>();

        if (usernameEmpty) {
            String usernameIsEmpty = "You must enter an username.";
            listOfErrors.add(usernameIsEmpty);
            inputHasErrors = true;
        }

        if (emailEmpty) {
            String emailIsEmpty = "You must enter a valid email.";
            listOfErrors.add(emailIsEmpty);
            inputHasErrors = true;
        }

        if (passwordEmpty) {
            String passwordIsEmpty = "You must enter a password.";
            listOfErrors.add(passwordIsEmpty);
            inputHasErrors = true;
        }

        if (passwordsNotMatch) {
            String passwordNotMatch = "Your passwords don't match.";
            listOfErrors.add(passwordNotMatch);
            inputHasErrors = true;
        }

        if(!Email.validate(email) && !emailEmpty) {
            String invalidEmail = "You have entered an invalid email format.";
            listOfErrors.add(invalidEmail);
            inputHasErrors = true;
        }

        if(!Password.validateLength(password)) {
            String invalidPassword = "Password must be minimum 4 characters long.";
            listOfErrors.add(invalidPassword);
            inputHasErrors = true;
        }

        if(userExists != null) {
            String usernameExists = "A username with that name already exists. Please try another username.";
            listOfErrors.add(usernameExists);
            inputHasErrors = true;
        }

        if (inputHasErrors) {
            request.getSession().setAttribute("listOfErrors", listOfErrors);
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
