package org.kj6682.samarcanda.item;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kj6682.samarcanda.Application;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Before
    public void setUp() throws Exception {
        logger.info(">>> setup ");
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllItems() throws Exception {
        logger.info(">>> getAllItems ");
        when(repository.findAll()).thenReturn(new ArrayList<Item>());
        mockMvc.perform(get("/items")).andExpect(status().isOk()).andExpect(content().string("[]"));
        logger.info("That's all folks");
    }

}
