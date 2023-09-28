package org.learning.springlamiapizzeriacrud.service;

import org.learning.springlamiapizzeriacrud.model.Ingredient;
import org.learning.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient getById(Long id) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(
                id);
        if (ingredientOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ingredientOptional.get();
    }

    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }

    public void save(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public void update(Ingredient ingredient) {
        if (!ingredientRepository.existsById(ingredient.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        ingredientRepository.save(ingredient);
    }

}
