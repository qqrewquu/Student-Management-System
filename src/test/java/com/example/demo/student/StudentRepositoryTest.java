package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;


    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckWhenStudentEmailExists() {
        // given
        Student studentOne = new Student("gary", "gary.guo@queensu.ca", Gender.MALE);
        underTest.save(studentOne);
        // when
        boolean expected = underTest.selectExistsEmail("gary.guo@queensu.ca");
        // then
        assertThat(expected).isTrue();

    }
    @Test
    void itShouldCheckWhenStudentEmailNotExists() {
        // given
        String email = "gary.guo@queensu.ca";

        // when
        boolean expected = underTest.selectExistsEmail(email);
        // then
        assertThat(expected).isFalse();

    }


}