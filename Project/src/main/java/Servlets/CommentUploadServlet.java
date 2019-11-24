package Servlets;

import BDlogical.UsersRepository;
import BDlogical.UsersRepositoryImpl;
import Model.Post;
import Model.User;
import com.sun.org.apache.bcel.internal.generic.ILOAD;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/uploadComment")
public class CommentUploadServlet extends HttpServlet {
    private UsersRepository repository;

    @Override
    public void init() throws ServletException {
        repository = UsersRepositoryImpl.getRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Long userId = (Long) session.getAttribute("id");
        Long postId = Long.parseLong(req.getParameter("postId"));
        Optional<Post> optionalPost = repository.findPost(postId);

        Post post;
        if(optionalPost.isPresent()){
            post = optionalPost.get();
        }else {
            throw  new IllegalStateException("Post not found by id");
        }

        Optional<User> userOptional = repository.findById(post.getUsersId());
        User userVisit;
        if(userOptional.isPresent()){
            userVisit = userOptional.get();
        }else {
            throw  new IllegalStateException("User not found by id");
        }

        Long userVisitId = userVisit.getId();

        String text = req.getParameter("textComment");
        repository.saveComment(postId, text, userId);

        resp.sendRedirect(req.getContextPath() + "/user?id=" + userVisitId);
    }
}
