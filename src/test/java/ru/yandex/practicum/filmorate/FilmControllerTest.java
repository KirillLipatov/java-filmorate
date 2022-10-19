package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {

    private FilmController filmControllerr;

    @BeforeEach
    void create() {
        filmControllerr = new FilmController();
    }

    @Test
    public void shouldThrowsValidationExceptionWhenAddUserWithIdFromFilmsBase() {

        Film film1 = Film.builder()
                .name("Test1")
                .description("name111111111111")
                .releaseDate(LocalDate.of(2019, 1, 1))
                .duration(160)
                .build();
        filmControllerr.addFilm(film1);

        Film film2 = Film.builder()
                .name("Test")
                .description("name222222222222222")
                .releaseDate(LocalDate.of(2000, 2, 1))
                .duration(90)
                .build();
        film2.setId(1);

        final ValidationException validationException = assertThrows(ValidationException.class,
                () -> filmControllerr.addFilm(film2));
        assertEquals("Фильм с id - " + film2.getId() + "уже существует",
                validationException.getMessage(), "Исключение не выброшено");
    }

    @Test
    public void whenUpdatedFilmWithUnknownIdThenThrowsValidationException() {

        Film film1 = Film.builder()
                .name("Test1")
                .description("name111111111111")
                .releaseDate(LocalDate.of(2019, 1, 1))
                .duration(160)
                .build();
        filmControllerr.addFilm(film1);

        Film updateFilm = Film.builder()
                .name("Test")
                .description("name222222222222222")
                .releaseDate(LocalDate.of(2000, 2, 1))
                .duration(90)
                .build();
        updateFilm.setId(0);

        final ValidationException validationException = assertThrows(ValidationException.class,
                () -> filmControllerr.update(updateFilm));
        assertEquals("Фильм с id - " + updateFilm.getId() + " не существует",
                validationException.getMessage(), "Исключение не выброшено");
    }
}