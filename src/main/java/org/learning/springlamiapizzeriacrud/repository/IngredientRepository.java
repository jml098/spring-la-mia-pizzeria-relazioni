package org.learning.springlamiapizzeriacrud.repository;

import org.learning.springlamiapizzeriacrud.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {}
