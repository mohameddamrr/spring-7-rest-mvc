package guru.springframework.spring7restmvc.service;

import guru.springframework.spring7restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    void deleteBeerById(UUID beerId);

    void updateBeerById(UUID id, BeerDTO beerDTO);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    void updateBeerPatchById(UUID beerId, BeerDTO beerDTO);
}
