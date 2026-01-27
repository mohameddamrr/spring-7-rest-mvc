package guru.springframework.spring7restmvc.service;

import guru.springframework.spring7restmvc.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    void deleteBeerById(UUID beerId);

    void updateBeerById(UUID id, Beer beer);

    Beer saveNewBeer(Beer beer);

    List<Beer> listBeers();

    Optional<Beer> getBeerById(UUID id);

    void updateBeerPatchById(UUID beerId, Beer beer);
}
