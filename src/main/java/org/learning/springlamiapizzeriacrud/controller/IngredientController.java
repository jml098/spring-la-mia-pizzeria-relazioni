package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Ingredient;
import org.learning.springlamiapizzeriacrud.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("ingredients")
public class IngredientController {


    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredients", ingredientService.getAll());
        return "ingredient/index";
    }

    @GetMapping("{id}")
    public String ingredient(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.getById(id));

        return "ingredient/detail";
    }

    @GetMapping("create")
    public String createIngredient(Model model) {
        model.addAttribute("ingredient", new Ingredient());

        return "ingredient/form";
    }

    @GetMapping("edit/{id}")
    public String editIngredient(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.getById(id));

        return "ingredient/form";
    }

    @PostMapping("create")
    public String createIngredient(
            @Valid @ModelAttribute Ingredient ingredient,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "ingredient/form";
        }

        ingredientService.save(ingredient);

        return "redirect:/ingredients";
    }

    @PostMapping("edit/{id}")
    public String editIngredient(
            @PathVariable Long id,
            @Valid @ModelAttribute Ingredient ingredient,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "ingredient/form";
        }

        ingredientService.update(ingredient);

        return "redirect:/ingredients";
    }

    @PostMapping("delete/{id}")
    public String deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteById(id);

        return "redirect:/ingredients";
    }

}
