package Servlets;

import BDlogical.UsersRepository;
import BDlogical.UsersRepositoryImpl;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet  extends HttpServlet {
    private UsersRepository repository;
    private boolean successReg = true;

    @Override
    public void init() throws ServletException {
        repository = UsersRepositoryImpl.getRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!successReg){
            req.setAttribute("wrong_mail", "Эта почта уже занята");
            successReg = true;
        }
        req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String mail = req.getParameter("mail");

        if(!repository.isExist(mail)){
            repository.save(new User(name, password, mail));
            Long id = (Long) repository.findUser(mail, password).get().getId();
            repository.saveImage(id, "28538.png");
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            successReg = false;
            resp.sendRedirect(req.getContextPath() + "/register");
        }
    }
}
