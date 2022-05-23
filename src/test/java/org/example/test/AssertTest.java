package org.example.test;

import org.example.AssertJ.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("Chaining Test -Object")
    void chainingTestUser() {
        User user = new User("Hans", 33);

        assertThat(user.getAge())
                .as("check1 %s's age", user.getName())
                .isEqualTo(33);
    }

    @Test
    @DisplayName("Filtering Test with Lambda")
    void lambdaTest() {
        List<User> userList = new ArrayList<>();
        User admin = new User("admin", 30);
        User user0 = new User("user0", 10);
        User user1 = new User("user1", 20);

        userList.add(admin);
        userList.add(user0);
        userList.add(user1);

        assertThat(userList)
                .filteredOn(user -> user.getName().contains("admin"))
                .contains(admin);

        assertThat(userList)
                .filteredOn("age", notIn(30))
                .containsOnly(user0, user1);
    }
    
    @Test
    @DisplayName("Property 추출 테스트")
    void extractPropertyTest() {
        List<User> userList = new ArrayList<>();
        User admin = new User("admin", 30);
        User user0 = new User("user0", 10);
        User user1 = new User("user1", 20);
        userList.add(admin);
        userList.add(user0);
        userList.add(user1);

        assertThat(userList)
                .extracting("name", "age")
                .contains(tuple("admin", 30),
                          tuple("user0", 10),
                          tuple("user1", 20));
    }



}
