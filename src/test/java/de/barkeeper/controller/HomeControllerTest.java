package de.barkeeper.controller;

import de.barkeeper.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    public void homeTest() throws Exception {
        mockMvc.perform(get("/"));
    }

    /**
     * @Test
     * public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
     *   throws Exception {
     *
     *     Employee alex = new Employee("alex");
     *
     *     List<Employee> allEmployees = Arrays.asList(alex);
     *
     *     given(service.getAllEmployees()).willReturn(allEmployees);
     *
     *     mvc.perform(get("/api/employees")
     *       .contentType(MediaType.APPLICATION_JSON))
     *       .andExpect(status().isOk())
     *       .andExpect(jsonPath("$", hasSize(1)))
     *       .andExpect(jsonPath("$[0].name", is(alex.getName())));
     * }
     */
}
