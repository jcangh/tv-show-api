package com.real.tv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.real.tv.dto.TokenRequest;
import com.real.tv.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokenIntegrationTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Test
    public void authenticateUserSuccess() throws Exception {
        String jsonDto = om.writeValueAsString(createTokenRequest("admin"));

        this.mvc.perform(post("/v1/users/authenticate")
                .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void authenticateUserFail() throws Exception {
        String jsonDto = om.writeValueAsString(createTokenRequest("notExistingUser"));

        this.mvc.perform(post("/v1/users/authenticate")
                .content(jsonDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isPreconditionFailed());
    }

    private TokenRequest createTokenRequest(String username){
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setPassword("admin123");
        tokenRequest.setUsername(username);
        return tokenRequest;
    }
}
