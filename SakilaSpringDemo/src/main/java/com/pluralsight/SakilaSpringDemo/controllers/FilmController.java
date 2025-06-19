package com.pluralsight.SakilaSpringDemo.controllers;

import com.pluralsight.SakilaSpringDemo.dao.FilmDao;
import com.pluralsight.SakilaSpringDemo.models.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {

    @Autowired
    @Qualifier("jdbcFilmDao")
    private FilmDao filmDao;

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmDao.getAll();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmDao.findById(id);
    }

    @PostMapping("/films")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Film addFilm(@RequestBody Film film) {
        return filmDao.add(film);
    }

    @PutMapping("/films/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFilm(@PathVariable int id, @RequestBody Film film) {
        //filmDao.update(id, film);
    }

    @DeleteMapping("/films/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable int id) {
        //filmDao.delete(id);
    }
}
