package Servlets;

import BDlogical.UsersRepository;
import BDlogical.UsersRepositoryImpl;
import Model.Post;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UsersRepository repository;
    private Long thisId = 1L;

    @Override
    public void init() throws ServletException {
        repository = UsersRepositoryImpl.getRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idUserStr = req.getParameter("id");
        Long idUser;
        try {
            idUser = Long.parseLong(idUserStr);
            thisId = idUser;
        } catch (NumberFormatException e) {
            idUser = 1L;
        }

        HttpSession session = req.getSession(false);
        Long id = (Long) session.getAttribute("id");
        id = id == null ? -1 : id;
        if (id.equals(idUser)) {
            resp.sendRedirect(req.getContextPath() + "/profile");

        } else {
            User user = repository.findById(idUser).get();
            String image = repository.findUserImage(user.getId());

            req.setAttribute("subscribers", repository.getCountSubscribers(user.getId()));
            req.setAttribute("subscriptions", repository.getCountSubscription(user.getId()));

            if (id > 0) {
                boolean isSub = repository.isSubscription(id, user.getId());
                int sub = (isSub) ? 1 : 0;
                req.setAttribute("subscribe", sub);
            }

            req.setAttribute("Image", image);
            req.setAttribute("userName", user.getName());

            List<Post> list = repository.findAllPosts(user.getId());
            req.setAttribute("posts", list);
            req.getRequestDispatcher("/jsp/user.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String subscribe = req.getParameter("subscribe");
        String unsubscribe = req.getParameter("unsubscribe");
        Long id = (Long) session.getAttribute("id");

        if (subscribe != null) {
            repository.subscribe(id, thisId);
        } else if (unsubscribe != null) {
            repository.unsubscribe(id, thisId);
        }
        resp.sendRedirect(req.getContextPath() + "/user?id=" + thisId);
    }
}
