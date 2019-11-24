package Servlets;

import BDlogical.UsersRepository;
import BDlogical.UsersRepositoryImpl;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.Optional;

@WebServlet("/profile/edit")
@MultipartConfig
public class EditServlet extends HttpServlet {
    private UsersRepository repository;

    @Override
    public void init() throws ServletException {
        repository = UsersRepositoryImpl.getRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Long id = (Long) session.getAttribute("id");

        String name;
        String mail;
        Optional<User> optional = repository.findById(id);
        if(optional.isPresent()) {
            User user = optional.get();
            name = user.getName();
            mail = user.getMail();
        }else{
            throw new IllegalStateException("Ошибка в получении id");
        }

        req.setAttribute("userName", name);

        req.getRequestDispatcher("/jsp/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
        resp.sendRedirect(req.getContextPath() + "/profile/edit");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        final String path = "/home/nail/Progy/Semester/Project/target/Project-0.0.1/images";
        final String newName = request.getParameter("username");
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);

        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("id");

        //парсер
        String name = nameParser(newName);

        if(!newName.equals("")) {
            repository.changeName(id, name);
        }

        if(fileName.length() != 0){

            repository.changeImage(id, fileName);

            OutputStream out = null;
            InputStream filecontent = null;


            try {
                out = new FileOutputStream(new File(path + File.separator
                        + fileName));
                filecontent = filePart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            } catch (FileNotFoundException fne) {
            } finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            }
        }


    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String nameParser(String name){
        String[] split = name.split(" ");

        String result = "";
        for(String i : split){
            result += i;
        }
        return result;
    }
}
