package user;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by luigi on 30/09/15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SampleApplication.class)
@WebAppConfiguration
public class UserIntegrationTest {

    //Declare controller and inject mocks
    @Inject
    private WebApplicationContext webApplicationContext;

    //Mock the Repository
    @Mock
    private UserRepository repository;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));
    }

    public void createUser() throws Exception{

        User userIn = new User();
        userIn.setName("George Orwell");
        userIn.setId(0L);
        userIn.setRole("admin");
        userIn.setAddress("MiniLuv");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/users")).andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("**************************"+result);
        Assert.assertNotNull(result);
    }

}
