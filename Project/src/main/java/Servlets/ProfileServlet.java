package Servlets;

import BDlogical.UsersRepository;
import BDlogical.UsersRepositoryImpl;
import Model.Post;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private UsersRepository repository;

    @Override
    public void init() throws ServletException {
        repository = UsersRepositoryImpl.getRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        Long id = (Long) session.getAttribute("id");

        Optional<User >userOptional = repository.findById(id);
        User user = userOptional.get();

        req.setAttribute("subscribers", repository.getCountSubscribers(user.getId()));
        req.setAttribute("subscriptions", repository.getCountSubscription(user.getId()));

        String image = repository.findUserImage(user.getId());
        req.setAttribute("Image", image);
        req.setAttribute("userName", user.getName());

        List<Post> list = repository.findAllPosts(user.getId());
        req.setAttribute("posts", list);
        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("exit");
        if (name != null) {
            Cookie cookie = new Cookie("mail", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);

            cookie = new Cookie("password", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);

            req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
        }
    }
}
