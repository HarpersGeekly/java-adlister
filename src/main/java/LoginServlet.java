import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by RyanHarper on 1/31/17.
 */
@WebServlet(name = "FormServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username.equalsIgnoreCase("admin") &&
            password.equalsIgnoreCase("password")) {

            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login"); //if the username and password isn't admin and password it redirects back to login
        }
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
