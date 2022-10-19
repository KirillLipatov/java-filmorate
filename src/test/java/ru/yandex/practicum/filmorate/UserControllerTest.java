package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void create() {
        userController = new UserController();
    }

    @Test
    public void shouldThrowsValidationExceptionWhenAddUserWithKnownId() {

        User user = User.builder()
                .login("Test1")
                .name("name1")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(2022, 1, 1))
                .build();
        userController.addUser(user);

        User updatedUser = User.builder()
                .login("Test1")
                .name("name1")
                .email("mail_@mail.ru")
                .birthday(LocalDate.of(2022, 1, 1))
                .build();
        updatedUser.setId(1);

        final ValidationException validationException = assertThrows(ValidationException.class,
                () -> userController.addUser(updatedUser));
        assertEquals("Пользователь с id - " + updatedUser.getId() + "уже существует",
                validationException.getMessage(), "Исключение не выброшено");
    }

    @Test
    public void whenUpdatedWithUnknownIdThenThrowsValidationException() {

        User user = User.builder()
                .login("Test1")
                .name("name2")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(2022, 1, 1))
                .build();
        userController.addUser(user);

        User updatedUser = User.builder()
                .login("Test1")
                .name("name2")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(2022, 1, 1))
                .build();
        updatedUser.setId(0);

        final ValidationException validationException = assertThrows(ValidationException.class,
                () -> userController.updateUser(updatedUser));
        assertEquals("Пользователь с id - " + updatedUser.getId() + " не существует",
                validationException.getMessage(), "Исключение не выброшено");
    }

}