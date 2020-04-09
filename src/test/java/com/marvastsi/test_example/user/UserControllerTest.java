package com.marvastsi.test_example.user;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import javax.transaction.Transactional;

import com.marvastsi.test_example.TestApplication;

//import org.apache.catalina.filters.CorsFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
 
@SpringBootTest(classes = { TestApplication.class,
        UserController.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
 
	@Autowired
    private MockMvc mockMvc;
 
    @SpyBean
    private UserService userServiceMock;
    
    @InjectMocks
	private UserController userController;
 
    @BeforeEach
    public void setUp() {
//    	MockitoAnnotations.initMocks(this);
//		mockMvc = MockMvcBuilders.standaloneSetup(userController)
//				.build();
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