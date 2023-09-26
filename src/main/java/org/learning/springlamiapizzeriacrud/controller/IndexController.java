package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class IndexController {
    private final PizzaRepository pizzaRepository;

    @Autowired
    public IndexController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizzaList = pizzaRepository.findAll();
        model.addAttribute("pizzas", pizzaList);
        return "index";
    }

    @GetMapping("/{id}")
    public String pizza(@PathVariable Long id, Model model) {

        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isPresent()) {
            model.addAttribute("pizza", pizzaOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "pizza/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());

        return "pizza/form";
    }

    @PostMapping("/create")
    public String createPizza(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "pizza/form";

        pizzaRepository.save(pizza);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {

        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isPresent()) {
            model.addAttribute("pizza", pizzaOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "pizza/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPizza(@PathVariable Long id, @Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "pizza/edit";

        pizzaRepository.save(pizza);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deletePizza(@PathVariable Long id) {
        pizzaRepository.deleteById(id);
        return "redirect:/";
    }
}
