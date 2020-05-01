package com.real.tv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.real.tv.dto.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Before
    public void cleanDatabase(){
        databaseCleanup.execute();
    }


    @Test
    public void createUserFailNotAuthorized() throws Exception {
        String jsonDto = om.writeValueAsString(createUser());

        this.mvc.perform(post("/v1/users")
                .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void createUserSuccess() throws Exception {
        String jsonDto = om.writeValueAsString(createUser());

        this.mvc.perform(post("/v1/users")
        .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void updateUserSuccess() throws Exception {
        String jsonDto = om.writeValueAsString(createUser());

        MvcResult result = this.mvc.perform(post("/v1/users")
                .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        String location = result.getResponse().getHeader("Location");
        Pattern pattern = Pattern.compile("(\\d+)$");
        Matcher matcher = pattern.matcher(location);
        matcher.find();
        Integer id = Integer.parseInt(matcher.group(), 10);

        this.mvc.perform(patch("/v1/users/"+id)
                .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void deleteUserSuccess() throws Exception {
        String jsonDto = om.writeValueAsString(createUser());

        MvcResult result = this.mvc.perform(post("/v1/users")
                .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        String location = result.getResponse().getHeader("Location");
        Pattern pattern = Pattern.compile("(\\d+)$");
        Matcher matcher = pattern.matcher(location);
        matcher.find();
        Integer id = Integer.parseInt(matcher.group(), 10);

        this.mvc.perform(delete("/v1/users/"+id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void getUsersSuccess() throws Exception {
        String jsonDto = om.writeValueAsString(createUser());

        this.mvc.perform(post("/v1/users")
                .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        final ResultActions result = this.mvc.perform(
                get("/v1/users")
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
        result.andExpect(jsonPath("$.length()").value("1"));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void getUserSuccess() throws Exception {
        String jsonDto = om.writeValueAsString(createUser());

        MvcResult result =this.mvc.perform(post("/v1/users")
                .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        String location = result.getResponse().getHeader("Location");
        Pattern pattern = Pattern.compile("(\\d+)$");
        Matcher matcher = pattern.matcher(location);
        matcher.find();
        Integer id = Integer.parseInt(matcher.group(), 10);

        final ResultActions getResult = this.mvc.perform(
                get("/v1/users/"+id)
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
        getResult.andExpect(jsonPath("$.username").value("staff"));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void getUserNotFound() throws Exception {
        String jsonDto = om.writeValueAsString(createUser());

        this.mvc.perform(post("/v1/users")
                .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        final ResultActions getResult = this.mvc.perform(
                get("/v1/users/9999")
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
    }


    private User createUser(){
        User user = new User();
        user.setPassword("12345");
        user.setUsername("staff");
        user.setRole("ROLE_USER");
        return user;
    }
}
