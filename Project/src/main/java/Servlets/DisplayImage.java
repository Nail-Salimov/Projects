package Servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/image")
public class DisplayImage extends HttpServlet {

    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws IOException
    {
        response.setContentType("image/jpeg");
        ServletOutputStream out;
        out = response.getOutputStream();

        String nameImage = request.getParameter("name");
        FileInputStream fin = new FileInputStream("/home/nail/Progy/Semester/Project/target/Project-0.0.1/images/" + nameImage);

        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        int ch =0; ;
        while((ch=bin.read())!=-1)
        {
            bout.write(ch);
        }

        bin.close();
        fin.close();
        bout.close();
        out.close();
    }
}