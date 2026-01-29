package guru.springframework.spring7restmvc.controller;

import guru.springframework.spring7restmvc.mappers.BeerMapper;
import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.service.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //so database state rolls back after each test
class BeerControllerIntegration {

    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() {
        List<BeerDTO> listBeers = beerController.listBeers();
        assertEquals(listBeers.size(), 3);

    }
    @Rollback
    @Transactional //so database state rolls back after each test
    @Test
    void testEmptyList(){
        beerRepository.deleteAll();
        List<BeerDTO> listBeers = beerController.listBeers();
        assertEquals(listBeers.size(), 0);
    }
}