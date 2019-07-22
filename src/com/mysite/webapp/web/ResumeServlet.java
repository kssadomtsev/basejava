package com.mysite.webapp.web;

import com.mysite.webapp.storage.Storage;
import com.mysite.webapp.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    Storage storage;

    public void init(ServletConfig config)
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("uuid");
        if (name == null) {
            request.setAttribute("storage", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/resumes.jsp").forward(request, response);
        } else {
            request.setAttribute("resume", storage.get(name));
            request.getRequestDispatcher("/WEB-INF/resume.jsp").forward(request, response);
        }
    }
}
