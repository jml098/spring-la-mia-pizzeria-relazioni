package org.learning.springlamiapizzeriacrud.service;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {

        this.pizzaRepository = pizzaRepository;
    }

    public List<Pizza> getAll() {

        return pizzaRepository.findAll();
    }

    public Pizza getById(Long id) {

        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return pizzaOptional.get();
    }

    public void deleteById(Long id) {

        pizzaRepository.deleteById(id);
    }

    public void save(Pizza pizza) {

        pizzaRepository.save(pizza);
    }

    public void update(Pizza pizza) {
        if (!pizzaRepository.existsById(pizza.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        pizzaRepository.save(pizza);
    }

}
