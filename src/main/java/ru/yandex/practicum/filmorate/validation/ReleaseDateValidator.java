package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.time.Month.DECEMBER;

/**
 * Реализация валидатора ReleaseDate
 */
public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    private static final LocalDate DATE_CREATE_CINEMA = LocalDate.of(1895, DECEMBER, 28);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value != null) {
            return value.isAfter(DATE_CREATE_CINEMA);
        }
        return true;
    }

}