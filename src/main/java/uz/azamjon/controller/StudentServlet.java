package uz.azamjon.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import uz.azamjon.model.Student;
import uz.azamjon.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class StudentServlet extends HttpServlet {
    StudentService studentService = new StudentService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        String line;

        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String json = sb.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(json, Student.class);
        PrintWriter out = response.getWriter();
        String responseMessage = "{}";
        if (studentService.isAlreadyExist(student.getPhoneNumber())) {
            response.setStatus(400);
            responseMessage = "{\"message\":\"There is already an account with that number\"}";
        } else {
            studentService.addStudent(student);
            response.setStatus(200);
            responseMessage = "{\"message\":\"Student has been added successfully\"}";
        }

        System.out.println(studentService.getAllStudents());

        //returns response to client
        response.setContentType("application/json");

        out.print(responseMessage);
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String idParam = request.getParameter("id");
        int idToDelete = Integer.parseInt(idParam);

        studentService.removeStudent(idToDelete);
        System.out.println(studentService.getAllStudents());

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String json = null;

        ObjectMapper objectMapper = new ObjectMapper();

        String pathInfo = request.getRequestURI();
        String[] pathSegments =  pathInfo.substring(1).split("/");

        if (pathSegments.length > 0) {
            String productId = pathSegments[pathSegments.length-1];
            if (productId.equals("students")) {
                json = objectMapper.writeValueAsString(studentService.getAllStudents());
            } else {
                Student s = studentService.getStudent(Integer.parseInt(productId));
                json = objectMapper.writeValueAsString(s);
            }

        }

        out.print(json);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String json = sb.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(json, Student.class);
        studentService.updateStudent(student);
    }

}
