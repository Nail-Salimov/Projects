package Servlets;

import BDlogical.UsersRepository;
import BDlogical.UsersRepositoryImpl;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UsersRepository repository;
    private boolean successLog = true;
    @Override
    public void init() throws ServletException {
        repository = UsersRepositoryImpl.getRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String cookieMail = null;
        String cookiePassword = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("mail")) {
                    cookieMail = cookie.getValue();
                } else if (name.equals("password")) {
                    cookiePassword = cookie.getValue();
                }
            }
        }
        Optional<User> userOptional = repository.findUser(cookieMail, cookiePassword);
        if (userOptional.isPresent()) {
            HttpSession httpSession = req.getSession();
            User user = userOptional.get();
            httpSession.setAttribute("mail", user.getMail());
            httpSession.setAttribute("id", user.getId());
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else {
            if(!successLog){
                req.setAttribute("wrong_parameter", "неправильный логин или пароль");
            }
            req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String password = req.getParameter("password");
        String mail = req.getParameter("mail");
        String rememberMe = req.getParameter("remember_me");

        Pattern pattern = Pattern.compile("^.{2,}@\\w{2,}\\.\\w{2,}$");
        Matcher matcher = pattern.matcher(mail);

        if(!matcher.find()){
            successLog = false;
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {

            Optional<User> userOptional = repository.findUser(mail, password);
            if (userOptional.isPresent()) {
                HttpSession httpSession = req.getSession();
                User user = userOptional.get();
                httpSession.setAttribute("mail", user.getMail());
                httpSession.setAttribute("id", user.getId());

                if (rememberMe != null) {
                    Cookie cookieMail = new Cookie("mail", mail);
                    cookieMail.setMaxAge(24 * 60 * 60);
                    resp.addCookie(cookieMail);

                    Cookie cookiePassword = new Cookie("password", password);
                    cookiePassword.setMaxAge(24 * 60 * 60);
                    resp.addCookie(cookiePassword);
                }
                resp.sendRedirect(req.getContextPath() + "/profile");
            } else {
                successLog = false;
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }
    }
}
