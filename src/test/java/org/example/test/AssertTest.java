package org.example.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class AssertTest {

    @Test
    @DisplayName("Chaining Test -String Type")
    void chainingTestString() {
        assertThat("Hello, World! Nice to meet you.")
                .isNotEmpty()
                .contains("Nice")
                .contains("World")
                .doesNotContain("Give Up")
                .startsWith("Hello")
                .endsWith("you.")
                .isEqualTo("Hello, World! Nice to meet you.");
    }

    @Test
    @DisplayName("Chaining Test -Number")
    void chainingTestNumber() {
        assertThat(3.14d)
                .isPositive()
                .isGreaterThan(3)
                .isLessThan(4)
                .isEqualTo(3, offset(1d))
                .isEqualTo(3.1, offset(3.1d))
                .isEqualTo(3.14, offset(3.14d))
                .isEqualTo(3.14);
    }

//    @Test
//    @DisplayName("Chaining Test -Object")
//    void chainingTestUser() {
//        assertThat()
//    }
}
