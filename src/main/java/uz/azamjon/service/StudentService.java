package uz.azamjon.service;

import uz.azamjon.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private static int idGenerator = 0;

    private List<Student> allStudents = new ArrayList<>();

    public List<Student> getAllStudents() {
        return allStudents;
    }

    public void addStudent(Student student) {
        if (student.getId() == 0) {
            student.setId(++idGenerator);
        } else {
            throw new IllegalArgumentException("Student already exists");
        }
        allStudents.add(student);
    }

    public void removeStudent(int id) {
        Student student = getStudent(id);

        if (student == null) {
            throw new IllegalArgumentException("Student does not exist");
        }

        allStudents.remove(student);
    }

    public Student getStudent(int id) {
        for (Student student : allStudents) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public boolean isAlreadyExist(int phoneNumber) {
        for (Student student : allStudents) {
            if (student.getPhoneNumber() == phoneNumber) {
                return true;
            }
        }
        return false;
    }
}
