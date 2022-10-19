package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.Marker;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> filmsBase = new HashMap<>();
    private int id = 1;

    @PostMapping()
    @Validated(Marker.OnCreate.class)
    public Film addFilm(@Valid @RequestBody Film film) {
        log.debug("Переданный объект: {}", film);

        if (filmsBase.containsKey(film.getId())) {
            log.info("Фильм с id - {} уже существует", film.getId());
            throw new ValidationException("Фильм с id - " + film.getId() + "уже существует");
        } else {
            film.setId(id);
            filmsBase.put(film.getId(), film);
            id = film.getId() + 1;
        }
        return film;
    }

    @PutMapping()
    @Validated(Marker.OnUpdate.class)
    public Film update(@Valid @RequestBody Film film) {
        log.debug("Переданный объект: {}", film);

        if (!filmsBase.containsKey(film.getId())) {
            log.info("Фильм с id - {} не существует", film.getId());
            throw new ValidationException("Фильм с id - " + film.getId() + " не существует");
        } else {
            filmsBase.replace(film.getId(), film);
        }
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmsBase.values();
    }

}