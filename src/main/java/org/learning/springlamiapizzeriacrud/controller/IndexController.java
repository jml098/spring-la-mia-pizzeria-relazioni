package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.model.SpecialOffer;
import org.learning.springlamiapizzeriacrud.service.PizzaService;
import org.learning.springlamiapizzeriacrud.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class IndexController {

    private final PizzaService pizzaService;
    private final SpecialOfferService specialOfferService;


    @Autowired
    public IndexController(
            PizzaService pizzaService, SpecialOfferService specialOfferService
    ) {
        this.pizzaService = pizzaService;
        this.specialOfferService = specialOfferService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pizzas", pizzaService.getAll());

        return "index";
    }

    @GetMapping("/{id}")
    public String pizza(
            @PathVariable Long id, Model model
    ) {
        model.addAttribute("pizza", pizzaService.getById(id));
        model.addAttribute("specialOffer", new SpecialOffer());

        return "pizza/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());

        return "pizza/form";
    }

    @PostMapping("/create")
    public String createPizza(
            @Valid @ModelAttribute("pizza") Pizza pizza,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "pizza/form";

        pizzaService.save(pizza);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            Model model, @PathVariable Long id
    ) {
        model.addAttribute("pizza", pizzaService.getById(id));

        return "pizza/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPizza(
            @PathVariable Long id,
            @Valid @ModelAttribute("pizza") Pizza pizza,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "pizza/edit";

        pizzaService.save(pizza);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deletePizza(@PathVariable Long id) {
        pizzaService.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/special-offers/create")
    public String createSpecialOffer(
            Model model, @RequestParam("pizzaId") Long pizzaId
    ) {
        SpecialOffer specialOffer = new SpecialOffer();

        specialOffer.setPizza(pizzaService.getById(pizzaId));

        model.addAttribute("specialOffer", specialOffer);
        return "special-offer/form";
    }

    @GetMapping("/special-offers/edit/{id}")
    public String editSpecialOffer(
            Model model, @PathVariable Long id
    ) {
        model.addAttribute("specialOffer", specialOfferService.getById(id));

        return "special-offer/form";
    }

    @PostMapping("/special-offers/create")
    public String createSpecialOffer(
            @Valid @ModelAttribute("specialOffer") SpecialOffer specialOffer,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "special-offer/form";

        specialOfferService.save(specialOffer);

        return "redirect:/" + specialOffer.getPizza().getId();
    }

    @PostMapping("/special-offers/edit/{id}")
    public String editSpecialOffer(
            @PathVariable Long id,
            @Valid @ModelAttribute("specialOffer") SpecialOffer specialOffer,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/" + specialOffer.getPizza().getId();
        }

        specialOffer.setId(id);
        specialOfferService.save(specialOffer);

        return "redirect:/" + specialOffer.getPizza().getId();
    }

    @PostMapping("/special-offers/delete/{id}")
    public String deleteSpecialOffer(@PathVariable Long id) {
        Long pizzaId = specialOfferService.getById(id).getPizza().getId();
        specialOfferService.deleteById(id);

        return "redirect:/" + pizzaId;
    }

}
