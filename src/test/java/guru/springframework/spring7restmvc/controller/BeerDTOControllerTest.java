package guru.springframework.spring7restmvc.controller;

import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.service.BeerService;

import guru.springframework.spring7restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static guru.springframework.spring7restmvc.controller.BeerController.BEER_PATH;
import static guru.springframework.spring7restmvc.controller.BeerController.BEER_PATH_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerDTOControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;


    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testPatchBeerById() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch(BEER_PATH_ID, beerDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<UUID> uuidCaptor = ArgumentCaptor.forClass(UUID.class);
        ArgumentCaptor<BeerDTO> beerCaptor = ArgumentCaptor.forClass(BeerDTO.class);

        verify(beerService).updateBeerPatchById(uuidCaptor.capture(), beerCaptor.capture());

        assertThat(beerDTO.getId()).isEqualTo(uuidCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerCaptor.getValue().getBeerName());


    }


    @Test
    void DeleteBeerById() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BEER_PATH_ID, beerDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteBeerById(any(UUID.class));
    }


    @Test
    void updateBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);
        beerDTO.setBeerStyle(BeerStyle.IPA);
        beerDTO.setBeerName("Updated");
        beerDTO.setPrice(BigDecimal.valueOf(12.99));
        beerDTO.setUpc("88");
        beerDTO.setQuantityOnHand(77);

//        given(beerService.updateBeerById(beer.getId(),beer)); as it doesnt return

        mockMvc.perform(put( BEER_PATH_ID, beerDTO.getId()).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isNoContent());


        verify(beerService).updateBeerById(any(UUID.class),any(BeerDTO.class)); //we use this instead of given as it doenst return
    }


    @Test
    void testCreateNewBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);
        beerDTO.setVersion(null);
        beerDTO.setId(null); //as we want to take this beer and create another beer using it so intially version and id =null

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BEER_PATH)
                .accept(MediaType.APPLICATION_JSON) // what you want back
                .contentType(MediaType.APPLICATION_JSON)   // what you are sending
                .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }



    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());
        mockMvc.perform(get(BEER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    void getBeerByIdNotFound() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());
        mockMvc.perform(get(BEER_PATH_ID,UUID.randomUUID()))
        .andExpect(status().isNotFound());


    }

    @Test
    void getBeerById() throws Exception {
        BeerDTO testBeerDTO = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(testBeerDTO.getId())).willReturn(Optional.of(testBeerDTO));

        mockMvc.perform(get(BEER_PATH_ID, testBeerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is (testBeerDTO.getId().toString())))
                .andExpect(jsonPath("$.beerName",is (testBeerDTO.getBeerName())));
    }
}