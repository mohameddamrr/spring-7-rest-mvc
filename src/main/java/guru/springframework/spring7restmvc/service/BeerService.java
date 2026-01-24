package guru.springframework.spring7restmvc.service;

import guru.springframework.spring7restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    Beer saveNewBeer(Beer beer);

    List<Beer> listBeers();

    Beer getBeerById(UUID id);
}
