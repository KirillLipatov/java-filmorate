package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
public class Film {

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
   // @Null(groups = Marker.OnCreate.class, message = "id должно быть пустым")
   // @NotNull(groups = Marker.OnUpdate.class, message = "Не указан id")
    private int id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private final String name;

    @Size(max = 200, message ="Превышена максимальная длина описания в 200 символов" )
    private final String description;

    @ReleaseDate
    private final LocalDate releaseDate;

    @Min(value = 0, message = "Продолжительность фильма должна быть положительной")
    private final int duration;

}