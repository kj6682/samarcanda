package org.kj6682.samarcanda.item;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kj6682.samarcanda.Application;
import org.kj6682.samarcanda.org.kj6682.samarcanda.location.Location;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by luigi on 30/09/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ItemTest {

    private final static Logger logger = LoggerFactory.getLogger(ItemTest.class);

    //Declare controller and inject mocks
    @InjectMocks
    private ItemController controller;

    //Mock the Repository
    @Mock
    private ItemRepository repository;

    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    @Before
    public void setUp() throws Exception {
        logger.info(">>> setup ");
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllItems_empty() throws Exception {
        logger.info(">>> getAllItems_empty ");
        when(repository.findAll()).thenReturn(new ArrayList<Item>());
        mockMvc.perform(get("/items")).andExpect(status().isOk()).andExpect(content().string("[]"));
        logger.info("<<< getAllItems_empty");
    }


    /**
     * expected
     * [{"id":null,"title":"item1","by":"author of item1","location":{"id":"1","site":"stanza rossa","store":"armadio A","shelf":"scaffale 3"}}]
     * @throws Exception
     */
    @Test
    public void getAllItems_oneItem() throws Exception {
        logger.info(">>> getAllItems_oneItem");

        //prepare
        String expected = "[{\"id\":null,\"title\":\"item1\",\"by\":\"author of item1\",\"location\":{\"id\":\"1\",\"site\":\"stanza rossa\",\"store\":\"armadio A\",\"shelf\":\"scaffale 3\"}}]";

        Location location = new Location();
        location.setId("1");
        location.setSite("site");
        location.setStore("store");
        location.setShelf("shelf");

        Item item = new Item("item1","author of item1", location);

        List<Item> items = new ArrayList<Item>();
        items.add(item);
        when(repository.findAll()).thenReturn(items);

        //test
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(nullValue())))
                .andExpect(jsonPath("$[0].title", is("item1")))
                .andExpect(jsonPath("$[0].by", is("author of item1")))
                .andExpect(jsonPath("$[0].location.id", is("1")))
                .andExpect(jsonPath("$[0].location.site", is("site")))
                .andExpect(jsonPath("$[0].location.store", is("store")))
                .andExpect(jsonPath("$[0].location.shelf", is("shelf")))
        ;

        logger.info("<<< getAllItems_oneItem");
    }

    /**
     * expected
     * [{"id":null,"title":"item1","by":"author of item1","location":{"id":"1","site":"stanza rossa","store":"armadio A","shelf":"scaffale 3"}},
     *  {"id":null,"title":"item2","by":"author of item2","location":{"id":"1","site":"stanza rossa","store":"armadio A","shelf":"scaffale 3"}}]
     * @throws Exception
     */
    @Test
    public void getAllItems_twoItems() throws Exception {
        logger.info(">>> getAllItems_twoItems");

        //prepare

        Location location = new Location();
        location.setId("1");
        location.setSite("site");
        location.setStore("store");
        location.setShelf("shelf");

        Item item1 = new Item("item1","author of item1", location);
        Item item2 = new Item("item2","author of item2", location);

        List<Item> items = new ArrayList<Item>();
        items.add(item1);
        items.add(item2);
        when(repository.findAll()).thenReturn(items);

        //test
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(nullValue())))
                .andExpect(jsonPath("$[0].title", is("item1")))
                .andExpect(jsonPath("$[0].by", is("author of item1")))
                .andExpect(jsonPath("$[0].location.id", is("1")))
                .andExpect(jsonPath("$[0].location.site", is("site")))
                .andExpect(jsonPath("$[0].location.store", is("store")))
                .andExpect(jsonPath("$[0].location.shelf", is("shelf")))
                .andExpect(jsonPath("$[1].id", is(nullValue())))
                .andExpect(jsonPath("$[1].title", is("item2")))
                .andExpect(jsonPath("$[1].by", is("author of item2")))
                .andExpect(jsonPath("$[1].location.id", is("1")))
                .andExpect(jsonPath("$[1].location.site", is("site")))
                .andExpect(jsonPath("$[1].location.store", is("store")))
                .andExpect(jsonPath("$[1].location.shelf", is("shelf")))
        ;

        logger.info("<<< getAllItems_twoItems");
    }




}
