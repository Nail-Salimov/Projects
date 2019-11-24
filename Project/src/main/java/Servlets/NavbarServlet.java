package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/navbar")
public class NavbarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("edit") != null) {
            resp.sendRedirect(req.getContextPath() + "/profile/edit");

        } else if (req.getParameter("exit") != null) {

            System.out.println("delete cookie");
            Cookie cookie = new Cookie("mail", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);

            cookie = new Cookie("password", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);

            resp.sendRedirect(req.getContextPath() + "/login");
            //req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
        } else if (req.getParameter("profile") != null){
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else if(req.getParameter("search") != null){
            resp.sendRedirect(req.getContextPath() + "/search?s=" + req.getParameter("search"));
        }
    }
}
