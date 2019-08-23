package com.mysite.webapp.web;

import com.mysite.webapp.model.*;
import com.mysite.webapp.storage.Storage;
import com.mysite.webapp.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private static final LocalDate today = java.time.LocalDate.now();
    Storage storage;

    public void init(ServletConfig config) throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        final boolean newResume = uuid == null || uuid.length() == 0;
        if (newResume) {
            Resume r = new Resume(fullName);
            storage.save(r);
            r.setSection(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(
                    new Organization(new Link("", ""),
                            Arrays.asList(new Organization.Position(today, today, "", ""))))));
            r.setSection(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(
                    new Organization(new Link("", ""),
                            Arrays.asList(new Organization.Position(today, today, "", ""))))));
            request.setAttribute("resume", r);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
        } else {
            Resume r = storage.get(uuid);
            r.setFullName(fullName);
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim().length() != 0) {
                    r.setContact(type, value);
                } else {
                    r.getContacts().remove(type);
                }
            }
            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                System.out.println(type);
                // System.out.println(value);
                if (value != null && value.trim().length() != 0) {
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            r.setSection(type, new TextSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            r.setSection(type, new ListSection(value.split("\\n")));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            int organizationCount = Integer.valueOf(request.getParameter("organizationCount" + type));
                            System.out.println(organizationCount);
                            List<Organization> organizationList = new ArrayList<>(organizationCount);
                            for (int i = 0; i < organizationCount; i++) {
                                String urlTitle = request.getParameter("link" + type + i);
                                System.out.println(urlTitle);
                                String url = request.getParameter("linkUrl" + type + i);
                                System.out.println(url);
                                Link link = new Link(urlTitle, url.equals("") ? null : url);
                                System.out.println(link);
                                int posCount = Integer.valueOf(request.getParameter(type + String.valueOf(i) + "_posCount"));
                                List<Organization.Position> positionList = new ArrayList<>(posCount);
                                for (int j = 0; j < posCount; j++) {
                                    LocalDate startDate = LocalDate.parse(request.getParameter(type + String.valueOf(i) + "_" + j + "_start"));
                                    LocalDate endDate = LocalDate.parse(request.getParameter(type + String.valueOf(i) + "_" + j + "_end"));
                                    String title = request.getParameter(type + String.valueOf(i) + "_" + j + "_title");
                                    String description = request.getParameter(type + String.valueOf(i) + "_" + j + "_description");
                                    positionList.add(new Organization.Position(startDate, endDate, title, description));
                                }
                                organizationList.add(new Organization(link, positionList));
                            }
                            System.out.println(organizationList);
                            r.setSection(type, new OrganizationSection(organizationList));
                            break;
                    }
                } else {
                    r.getSections().remove(type);
                }
            }
            storage.update(r);
            response.sendRedirect("resume");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("storage", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/resumes.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
