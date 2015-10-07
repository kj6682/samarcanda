package user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kj6682.samarcanda.Application;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by luigi on 30/09/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserTest {

    //Declare controller and inject mocks
    @InjectMocks
    private UserController controller;

    //Mock the Repository
    @Mock
    private UserRepository repository;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllUsers_empty_list() throws Exception {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<User>());
        mockMvc.perform(MockMvcRequestBuilders.get("/users")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    public void getAllUsers_two_users() throws Exception {
        User one = new User();
        one.setName("George Orwell");
        one.setId(1L);
        one.setRole("user");
        one.setAddress("MiniLuv");

        User two = new User();
        two.setName("Franz Kafka");
        two.setId(2L);
        two.setRole("admin");
        two.setAddress("Amerika");

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(one, two));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("George Orwell")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].role", Matchers.is("user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Franz Kafka")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].role", Matchers.is("admin")));

        Mockito.verify(repository, Mockito.times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getUser_find_one_user() throws Exception {

        User found = new User();
        found.setName("Franz Kafka");
        found.setId(1L);
        found.setRole("admin");
        found.setAddress("Amerika");

        Mockito.when(repository.findOne(1L)).thenReturn(found);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Franz Kafka")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role", Matchers.is("admin")));

        Mockito.verify(repository, Mockito.times(1)).findOne(1L);
        verifyNoMoreInteractions(repository);

    }

    @Test
    public void createUser() throws Exception {
        User user = new User();
        user.setName("Franz Kafka");
        user.setId(1L);
        user.setRole("admin");
        user.setAddress("Amerika");

        Mockito.when(repository.save(Matchers.any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.asJsonByteStream(user))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository, Mockito.times(1)).save(captor.capture());

        verifyNoMoreInteractions(repository);

        User dtoArgument = captor.getValue();
        Assert.assertNotNull(dtoArgument.getId());
        Assert.assertThat(dtoArgument.getName(), Matchers.is("Franz Kafka"));
        Assert.assertThat(dtoArgument.getRole(), Matchers.is("admin"));
        Assert.assertThat(dtoArgument.getAddress(), Matchers.is("Amerika"));
    }

    @Test
    public void createUser_bad_method() throws Exception {
        User user = new User();
        user.setName("Franz Kafka");
        user.setId(1L);
        user.setRole("admin");
        user.setAddress("Amerika");

        Mockito.when(repository.save(Matchers.any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{id}", 1L)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.asJsonByteStream(user))
        )
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository, Mockito.times(0)).save(captor.capture());

    }
    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setName("Gregor Samsa");
        user.setId(1L);
        user.setRole("admin");
        user.setAddress("Metamorfosi");

        User updated = new User();
        updated.setName("Franz Kafka");
        updated.setId(1L);
        updated.setRole("admin");
        updated.setAddress("Amerika");

        Mockito.when(repository.save(Matchers.any(User.class))).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", 1L)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.asJsonByteStream(updated))
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository, Mockito.times(1)).save(captor.capture());

        verifyNoMoreInteractions(repository);

        User dtoArgument = captor.getValue();
        Assert.assertNotNull(dtoArgument.getId());
        Assert.assertThat(dtoArgument.getName(), Matchers.is("Franz Kafka"));
        Assert.assertThat(dtoArgument.getRole(), Matchers.is("admin"));
        Assert.assertThat(dtoArgument.getAddress(), Matchers.is("Amerika"));
    }

    @Test
    public void updateUser_bad_method() throws Exception {
        User user = new User();
        user.setName("Gregor Samsa");
        user.setId(1L);
        user.setRole("admin");
        user.setAddress("Metamorfosi");

        User updated = new User();
        updated.setName("Franz Kafka");
        updated.setId(1L);
        updated.setRole("admin");
        updated.setAddress("Amerika");

        Mockito.when(repository.save(Matchers.any(User.class))).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.asJsonByteStream(updated))
        )
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());

      }

}
