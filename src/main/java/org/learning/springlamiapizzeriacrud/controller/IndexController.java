package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.model.SpecialOffer;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.learning.springlamiapizzeriacrud.repository.SpecialOfferRepository;
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
    private final SpecialOfferRepository specialOfferRepository;

    @Autowired
    public IndexController(PizzaRepository pizzaRepository, SpecialOfferRepository specialOfferRepository) {
        this.pizzaRepository = pizzaRepository;
        this.specialOfferRepository = specialOfferRepository;
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
            model.addAttribute("specialOffer", new SpecialOffer());
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

    @GetMapping("/special-offers/create")
    public String createSpecialOffer(Model model, @RequestParam("pizzaId") Long pizzaId) {
        SpecialOffer specialOffer = new SpecialOffer();

        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
        if (pizza.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza not found");

        specialOffer.setPizza(pizza.get());


        model.addAttribute("specialOffer", specialOffer);
        return "special-offer/form";
    }

    @GetMapping("/special-offers/edit/{id}")
    public String editSpecialForm(Model model, @PathVariable Long id) {
        Optional<SpecialOffer> specialOffer = specialOfferRepository.findById(id);
        if (specialOffer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Special offer not found");

        model.addAttribute("specialOffer", specialOffer.get());
        return "special-offer/form";
    }

    @PostMapping("/special-offers/create")
    public String createSpecialOffer(@Valid @ModelAttribute("specialOffer") SpecialOffer specialOffer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "special-offer/form";

        specialOfferRepository.save(specialOffer);

        return "redirect:/" + specialOffer.getPizza().getId();
    }

    @PostMapping("/special-offers/edit/{id}")
    public String editSpecialOffer(@PathVariable Long id, @Valid @ModelAttribute("specialOffer") SpecialOffer specialOffer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "redirect:/" + specialOffer.getPizza().getId();

        specialOffer.setId(id);
        specialOfferRepository.save(specialOffer);

        return "redirect:/" + specialOffer.getPizza().getId();
    }

    @PostMapping("/special-offers/delete/{id}")
    public String deleteSpecialOffer(@PathVariable Long id) {
        Long pizzaId = specialOfferRepository.findById(id).get().getPizza().getId();
        specialOfferRepository.deleteById(id);
        return "redirect:/" + pizzaId;
    }
}
