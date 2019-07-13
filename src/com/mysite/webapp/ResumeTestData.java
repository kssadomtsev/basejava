package com.mysite.webapp;

import com.mysite.webapp.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;

public class ResumeTestData {

    private static final LocalDate today = java.time.LocalDate.now();

    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContact(ContactType.PHONE, "111");
        resume.setContact(ContactType.SKYPE, "@test1");
        resume.setContact(ContactType.EMAIL, "test1@test.ru");
        resume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/test1");
        resume.setContact(ContactType.GITHUB, "https://github.com/test1");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/test1");
        resume.setContact(ContactType.HOMEPAGE, "http://test1.ru/");
        /*
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        resume.setSection(SectionType.PERSONAL, new TextSection("Personal1"));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("A1", "A2", "A3")));
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Q1", "Q2", "Q3")));
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Organization(new Link("Organization1", "http://organization1.ru/"),
                        Arrays.asList(new Organization.Position(java.time.LocalDate.of(2013, 10, 1), today, "Specialist", "Some work things"))))));
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(
                new Organization(new Link("School1", "http://school11.ru/"),
                        Arrays.asList(new Organization.Position(java.time.LocalDate.of(2010, 10, 1), java.time.LocalDate.of(2013, 10, 1), "Student", "Learning"))))));
        */
        return resume;
    }

    public static void main(String[] args) {
        final String achievement = "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.\n" +
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\n" +
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.\n" +
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.\n" +
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).\n" +
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.";
        final String qualification = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n" +
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce\n" +
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,\n" +
                "MySQL, SQLite, MS SQL, HSQLDB\n" +
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,\n" +
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,\n" +
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).\n" +
                "Python: Django.\n" +
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js\n" +
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka\n" +
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.\n" +
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,\n" +
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.\n" +
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов\n" +
                "проектрирования, архитектурных шаблонов, UML, функционального\n" +
                "программирования\n" +
                "Родной русский, английский \"upper intermediate\"";
        final ArrayList<Organization> workOrganizations = new ArrayList<>();
        final ArrayList<Organization> educationOrganizations = new ArrayList<>();
        ArrayList<Organization.Position> positionArrayListWork = new ArrayList<>();
        ArrayList<Organization.Position> positionArrayListEducation = new ArrayList<>();
        final Resume r1 = new Resume("uuid1", "Григорий Кислин");
        r1.setContact(ContactType.PHONE, "+7(921) 855-0482");
        r1.setContact(ContactType.SKYPE, "grigory.kislin");
        r1.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        r1.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r1.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        r1.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        r1.setContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
        r1.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r1.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        r1.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList((achievement.split("\n")))));
        r1.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList((qualification.split("\n")))));
        positionArrayListWork.add(new Organization.Position(java.time.LocalDate.of(2013, 10, 1), today, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        workOrganizations.add(new Organization(new Link("Java Online Projects", "http://javaops.ru/"), positionArrayListWork));
        r1.setSection(SectionType.EXPERIENCE, new OrganizationSection(workOrganizations));
        positionArrayListEducation.add(new Organization.Position(java.time.LocalDate.of(2013, 3, 1), java.time.LocalDate.of(2013, 5, 1), "\"Functional Programming Principles in Scala\" by Martin Odersky", ""));
        educationOrganizations.add(new Organization(new Link("Coursera", "https://www.coursera.org/course/progfun"), positionArrayListEducation));
        r1.setSection(SectionType.EDUCATION, new OrganizationSection(educationOrganizations));
        System.out.println(r1);
        printContacts(r1);
        printSections(r1);
    }

    static void printContacts(Resume resume) {
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + ": " + resume.getContact(contactType));
        }
    }

    static void printSections(Resume resume) {
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType.getTitle() + ": " + resume.getSection(sectionType));
        }
    }
}