package org.kj6682.samarcanda.item;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kj6682.samarcanda.Application;
import org.kj6682.samarcanda.org.kj6682.samarcanda.location.Location;
import org.kj6682.samarcanda.org.kj6682.samarcanda.location.LocationController;
import org.kj6682.samarcanda.org.kj6682.samarcanda.location.LocationRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;

/**
 * Created by luigi on 30/09/15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
@WebAppConfiguration
public class LocationTest {

    //Declare controller and inject mocks
    @InjectMocks
    private LocationController controller;

    //Mock the Repository
    @Mock
    private LocationRepository repository;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllLocations() throws Exception{
        when(repository.findAll()).thenReturn(new ArrayList<Location>());
        mockMvc.perform(get("/locations")).andExpect(status().isOk()).andExpect(content().string("[]"));
    }

}
