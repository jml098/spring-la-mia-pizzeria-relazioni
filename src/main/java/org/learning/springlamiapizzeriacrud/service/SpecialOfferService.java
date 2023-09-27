package org.learning.springlamiapizzeriacrud.service;

import org.learning.springlamiapizzeriacrud.model.SpecialOffer;
import org.learning.springlamiapizzeriacrud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialOfferService {

    private final SpecialOfferRepository specialOfferRepository;


    @Autowired
    public SpecialOfferService(SpecialOfferRepository specialOfferRepository) {

        this.specialOfferRepository = specialOfferRepository;
    }


    public List<SpecialOffer> getAll() {

        return specialOfferRepository.findAll();
    }


    public SpecialOffer getById(Long id) {

        Optional<SpecialOffer> specialOfferOptional =
                specialOfferRepository.findById(id);

        if (specialOfferOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return specialOfferOptional.get();
    }


    public void deleteById(Long id) {

        specialOfferRepository.deleteById(id);
    }


    public void save(SpecialOffer specialOffer) {

        specialOfferRepository.save(specialOffer);
    }

}
