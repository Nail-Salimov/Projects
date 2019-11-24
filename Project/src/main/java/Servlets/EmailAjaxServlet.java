package Servlets;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/emailValidate")
public class EmailAjaxServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("asd");
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(req.getParameter("mail"));
        JSONObject object = new JSONObject();
        if(!matcher.matches()){
            object.put("content", "Invalid email!");
            resp.getWriter().write(object.toString());
        }
    }
}
