package org.example.test;

import org.assertj.core.api.SoftAssertions;
import org.example.AssertJ.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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

    @Test
    @DisplayName("SoftAssertion Test")
    void softAssertion() {
        int number1 = 10;
        int number2 = 20;
        String string1 = "abc";

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(number1).as("Test1").isLessThan(15);
        softAssertions.assertThat(number2).as("Test2").isGreaterThan(15);
        softAssertions.assertThat(string1).as("Test3").contains("b");

        // 이 코드로 위에 적힌 모든 softAssetions 실행.
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Exception Test")
    // 오류가 날 내용을 정확하게 검증하는 것.
    void exceptionTest() {
        Throwable throwable1 = catchThrowable(() -> {
            // 오류가 날 것을 적음.
            throw new IllegalAccessError("Exception1");
        });
        assertThat(throwable1).isInstanceOf(IllegalAccessError.class);
        assertThat(throwable1).hasMessage("Exception1");

        Throwable throwable2 = new ArithmeticException("0으로 나눌 수 없음");
        assertThat(throwable2).isInstanceOf(ArithmeticException.class);
        assertThat(throwable2).hasMessage("0으로 나눌 수 없음");
    }

    @Test
    @DisplayName("StringIndexOutOfBoundsException 확인")
    // default 값이 protected. protected, public, default 가능
    void charAt_범위밖() throws Exception {
        // given
        String input = "abc";

        System.out.println(input.charAt(input.length()));

        // when, then
        assertThatThrownBy(() -> input.charAt(input.length()))
                .isInstanceOf(StringIndexOutOfBoundsException.class)
                .hasMessageContaining("String index out of range")
                .hasMessageContaining(String.valueOf(input.length()));
    }

    @Test
    @DisplayName("NullPointerException 처리")
    void nullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> {
            throw new NullPointerException("Null!");
        })
                .withMessage("%s!", "Null")
                .withMessageContaining("Null")
                // exception 에러의 원인은 제공하지 않음.
                .withNoCause();

        Throwable throwable1 = new ArithmeticException("abcd");
        Throwable throwable2 = throwable1.getCause();
        throwable2.getMessage();
    }

    @Test
    @DisplayName("IllegalException 처리")
    void illegalArgs() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            throw new IllegalArgumentException("Illegal Args!");
        })
                .withMessage("%s!", "legal")
                .withMessageContaining("Illegal")
                .withNoCause();
    }

    @Test
    @DisplayName("IOException 처리")
    void ioException() {
        assertThatIOException().isThrownBy(() -> {
            throw new IOException("IO!");
        })
                .withMessage("%s!", "IO")
                .withMessageContaining("!!")
                .withNoCause();
    }

    @Test
    // 준비(Given) - 실행(When) - 검증(Then) : BDD 스타일
    @DisplayName("BDD Style - Exception")
    void exceptionAssertionExample() {
        // given some preconditions

        // when
        Throwable throwable1 = catchThrowable(() -> {
            throw new Exception("BOOM!");
        });

        // then
        assertThat(throwable1).isInstanceOf(Exception.class)
                .hasMessageContaining("doom!");
    }
}
