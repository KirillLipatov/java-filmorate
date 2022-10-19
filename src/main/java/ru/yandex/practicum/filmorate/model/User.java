package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Builder
public class User {

    /**
     * ПОЯСНЕНИЕ:
     *
     * @Null(groups = Marker.OnCreate.class, message = "id должно быть пустым")
     * @NotNull(groups = Marker.OnUpdate.class, message = "Не указан id")
     * <p>
     * Обе анотации работают отлично на валидацию id.
     * Однако когда они включены коллекция тестов в постмане от Яндекса заваливается на некоторых тестах.
     * Так как там другая логика и такая валидация не предусмотрена ТЗ
     */
    //@Null(groups = Marker.OnCreate.class, message = "id должно быть пустым")
    //@NotNull(groups = Marker.OnUpdate.class, message = "Не указан id")
    private int id;

    @Email(message = "Электронная почта не соответствует формату email")
    @NotBlank(message = "Электронная почта не может быть пустой")
    private final String email;

    @NotBlank(message = "Логин не может быть пустым и содержать пробелы")
    private final String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private final LocalDate birthday;

}