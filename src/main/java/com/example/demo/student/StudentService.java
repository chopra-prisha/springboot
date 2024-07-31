package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private static final Logger LOGGER = Logger.getLogger(StudentService.class.getName());

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();

    }
    public Page<Student> getStudents(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return studentRepository.findAll(pageable);
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");

        }
    }
    public Optional<Student> getStudentById(Long id) {
        LOGGER.info("Looking for student with ID: " + id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            LOGGER.info("Student found: " + student.get());
        } else {
            LOGGER.info("Student not found.");
        }
        return student;
    }
    public List<Student> getStudentsByName(String name) {
        LOGGER.info("Looking for students with name: " + name);
        List<Student> students = studentRepository.findStudentsByName(name);
        if (!students.isEmpty()) {
            LOGGER.info("Students found: " + students);
        } else {
            LOGGER.info("No students found with the name: " + name);
        }
        return students;
    }
    public List<Student> getStudentsByNameContaining(String namePart) {
        LOGGER.info("Looking for students with names containing: " + namePart);
        List<Student> students = studentRepository.findStudentsByNameContaining(namePart);
        if (!students.isEmpty()) {
            LOGGER.info("Students found: " + students);
        } else {
            LOGGER.info("No students found with names containing: " + namePart);
        }
        return students;
    }
    public double getAverageMarks() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .collect(Collectors.averagingDouble(Student::getMarks));
    }
    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("student with id " + id + " does not exist");
        }
        studentRepository.deleteById(id);
    }

    public void updateStudent(Long id, Student updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("student with id " + id + " does not exist"));

        if (updatedStudent.getName() != null && !updatedStudent.getName().isEmpty() && !updatedStudent.getName().equals(student.getName())) {
            student.setName(updatedStudent.getName());
        }

        if (updatedStudent.getEmail() != null && !updatedStudent.getEmail().isEmpty() && !updatedStudent.getEmail().equals(student.getEmail())) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(updatedStudent.getEmail());
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            student.setEmail(updatedStudent.getEmail());
        }

        if (updatedStudent.getDob() != null && !updatedStudent.getDob().equals(student.getDob())) {
            student.setDob(updatedStudent.getDob());
        }

        if (updatedStudent.getMarks() != null && !updatedStudent.getMarks().equals(student.getMarks())) {
            student.setMarks(updatedStudent.getMarks());
        }

        studentRepository.save(student);
    }



}
