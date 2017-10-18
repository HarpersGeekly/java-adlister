import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CounterServlet", urlPatterns = "/count")
public class CounterServlet extends HttpServlet {

    private int counter = 0;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        if(request.getParameter("reset") != null) {
            counter = 0;
        }
        counter += 1;

        request.setAttribute("counter", counter); // show the result of that process
        request.getRequestDispatcher("counter.jsp").forward(request, response); // give control to the jsp files
    }
}
