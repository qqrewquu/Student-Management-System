package com.example.demo.student;

import com.example.demo.Exceptions.BadRequestException;
import com.example.demo.Exceptions.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        // check if email is taken
        if (studentRepository.selectExistsEmail(student.getEmail())){
            throw new BadRequestException("The student email has been taken");
        }
        studentRepository.save(student);
    }


    public void deleteStudent(Long studentId) {
        // check if student id exists
        if(!studentRepository.existsById(studentId)){
            throw new StudentNotFoundException("Student " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }
}
