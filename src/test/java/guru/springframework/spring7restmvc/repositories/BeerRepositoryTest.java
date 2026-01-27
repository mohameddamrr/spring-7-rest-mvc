package guru.springframework.spring7restmvc.repositories;

import guru.springframework.spring7restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
     BeerRepository beerRepository;


    @Test
    void testsaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder().beerName("Test").build());
        assertNotNull(savedBeer);
        assertThat(savedBeer.getId()).isNotNull();


    }


}