package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.Marker;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Validated
@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> usersBase = new HashMap<>();
    private int id = 1;

    @PostMapping()
    @Validated({Marker.OnCreate.class})
    public User addUser(@Valid @RequestBody User user) {
        log.debug("Преданный объект: {}", user);
        if (usersBase.containsKey(user.getId())) {
            log.info("Пользователь с id - {} уже существует", user.getId());
            throw new ValidationException("Пользователь с id - " + user.getId() + "уже существует");
        } else if (user.getName() == null) {
            Stream.of(user)
                    .peek(f -> f.setName(f.getLogin()))
                    .peek(f -> f.setId(id))
                    .forEach(f -> usersBase.put(f.getId(), f));
            id = user.getId() + 1;
        } else {
            user.setId(id);
            usersBase.put(user.getId(), user);
            id = user.getId() + 1;
        }
        return user;
    }

    @PutMapping()
    @Validated({Marker.OnUpdate.class})
    public User updateUser(@Valid @RequestBody User user) {
        log.debug("Преданный объект: {}", user);
        if (!usersBase.containsKey(user.getId())) {
            log.info("Пользователь с id - {} не существует", user.getId());
            throw new ValidationException("Пользователь с id - " + user.getId() + " не существует");
        } else if (user.getName().isEmpty()) {
            Stream.of(user)
                    .peek(f -> f.setName(f.getLogin()))
                    .forEach(f -> usersBase.replace(f.getId(), f));
        } else {
            usersBase.replace(user.getId(), user);
        }
        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return usersBase.values();
    }

}