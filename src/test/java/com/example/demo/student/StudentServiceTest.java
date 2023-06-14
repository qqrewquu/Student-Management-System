package com.example.demo.student;

import com.example.demo.Exceptions.BadRequestException;
import com.example.demo.Exceptions.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {


    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {

        // when
        underTest.getAllStudents();
        // then

        // verify() -> check if the behavorial happen.
        // In our case, we call getAllStudents(), which use studentRepository to
        // invoke the findAll(). Then we check if findAll() has been invoked.

        // If we can verify().deleteAll(), it will fail b/c we did not call deleteAll() in this function
        verify(studentRepository).findAll();


    }

    @Test
    void canAddStudent() {

        // given
        Student student = new Student("gary", "gary.guo@gmail.com", Gender.MALE);
        // when
        underTest.addStudent(student);
        // then
        // get the Capture utility
        ArgumentCaptor<Student> studentArgumentCaptor =  ArgumentCaptor.forClass(Student.class);
        // check if studentReposiotry save student
        verify(studentRepository).save(studentArgumentCaptor.capture());
        // get the capturedStudent
        Student capturedStudent = studentArgumentCaptor.getValue();
        // compare capturedStudent with the student I added at the beginning
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken(){
        // given
        Student student = new Student("gary", "gary.guo@gmail.com", Gender.MALE);
        // when, then

        // make the mocked studentRepository already contains the email;
        // otherwise, the email will never be taken
        given(studentRepository.selectExistsEmail("gary.guo@gmail.com")) // can will use `selectExistsEmail(any())`
                .willReturn(true);


        assertThatThrownBy(()->underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("The student email has been taken");

        // check db does not save the student
        verify(studentRepository, never()).save(any());
    }

    @Test
    void willThrowWhenDeleteStudentNotExist() {
        // given
        Long studentId = 123L;

        // when, then
        // mock the student not exist in DB
        given(studentRepository.existsById(studentId))
                .willReturn(false);
        // check if throw the error
        assertThatThrownBy(()->underTest.deleteStudent(studentId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student " + studentId + " does not exist");
        // check db does not delete student
        verify(studentRepository, never()).deleteById(studentId);

    }

    @Test
    void canDeleteStudent(){
        // given
        Long studentId = 123L;

        // mock studentId exist in DB
        given(studentRepository.existsById(studentId))
                .willReturn(true);
        // when
        underTest.deleteStudent(studentId);
        // then

        verify(studentRepository).deleteById(studentId);
    }

}