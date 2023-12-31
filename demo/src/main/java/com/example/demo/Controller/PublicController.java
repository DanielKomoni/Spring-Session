package com.example.demo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Session.HttpSessionBean;
import com.example.demo.dbService.Film;
import com.example.demo.dbService.FilmService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model; 

@Controller
@RequestMapping("films")
public class PublicController {
    
    @Autowired 
    private HttpSessionBean httpSessionBean;

    @Autowired
    private FilmService filmService;


    @GetMapping("/allFilms")
    public String getCatalogue(Model model) {
        if(httpSessionBean.getName() == null) {
            return "redirect:/auth/login";
        }
        List<Film> films = filmService.getAllFilms();
        model.addAttribute("films", films);
        model.addAttribute("username", httpSessionBean.getName());
        return "allFilms";
    }

    @GetMapping("/filmId/{id}")
    public String getFilmById(Model model, @PathVariable int id) {
        if(httpSessionBean.getName() == null) {
            return "redirect:/auth/login";
        }

        Film film = filmService.getFilmById(id);
        model.addAttribute("film", film);
        return "film";
    }

    @GetMapping("/film/{title}")
    public String getFilmByTitle(Model model, @PathVariable String title) {
        if(httpSessionBean.getName() == null) {
            return "redirect:/auth/login";
        }

        Film film = filmService.getFilmByTitle(title);
        model.addAttribute("film", film);
        return "film";
    }

    @GetMapping("/allFilms/{tag}")
    public String getFilmsByTag(Model model, @PathVariable String tag) {
        if(httpSessionBean.getName() == null) {
            return "redirect:/auth/login";
        }

        List<Film> films = filmService.getFilmsByTag(tag);
        model.addAttribute("films", films);
        return "allFilms";
    }

    
    @GetMapping("/logout")
    public String invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate(); 
        }
        return "redirect:/films/allFilms"; 
    }
    
}
