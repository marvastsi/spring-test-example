package com.marvastsi.todo_project;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

//import org.apache.catalina.filters.CorsFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.marvastsi.todo_project.teste.User;
import com.marvastsi.todo_project.teste.UserController;
import com.marvastsi.todo_project.teste.UserService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = { TestApplication.class})
//@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class, TestApplication.class})
public class UserControllerTest {
 
    private MockMvc mockMvc;
 
    @MockBean
    private UserService userServiceMock;
    
    @InjectMocks
	private UserController userController;
 
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
//				.addFilters(new CorsFilter())
				.build();
    }
 
    @Test
    public void findAll_ShouldAddTodoEntriesToModelAndRenderTodoListView() throws Exception {
        User first = new User(1l, "Gamora");
        User second = new User(2l, "Nebula");
        when(userServiceMock.findAll()).thenReturn(Arrays.asList(first, second));
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("Gamora")))
	            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(2)))
	            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("Nebula")));
 
        verify(userServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(userServiceMock);
    }
}