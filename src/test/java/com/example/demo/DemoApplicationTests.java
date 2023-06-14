package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DemoApplicationTests {

	Calculator underTest = new Calculator();
	@Test
	void itShouldAddTwoNumbers() {
		// given
		int numOne = 20;
		int numtwo = 30;

		// when
		int result = underTest.add(numOne, numtwo);

		// then
		int expected = 50;
		assertThat(result).isEqualTo(expected);
	}

	class Calculator{
		public int add(int a, int b){
			return a + b;
		}
	}

}
