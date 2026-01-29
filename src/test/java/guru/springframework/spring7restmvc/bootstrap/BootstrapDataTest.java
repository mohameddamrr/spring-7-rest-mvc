package guru.springframework.spring7restmvc.bootstrap;

import guru.springframework.spring7restmvc.controller.BeerController;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BootstrapDataTest {
    @Autowired
    BeerRepository beerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData=new BootstrapData(beerRepository);
    }

    @Test
    void Testrun() throws Exception{
        bootstrapData.run(null);

        assertEquals(3L, beerRepository.count());
    }
}