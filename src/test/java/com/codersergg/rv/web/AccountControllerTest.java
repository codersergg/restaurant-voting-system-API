package com.codersergg.rv.web;

import com.codersergg.rv.UserTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.codersergg.rv.model.User;
import com.codersergg.rv.repository.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codersergg.rv.util.JsonUtil.writeValue;
import static com.codersergg.rv.web.AccountController.URL;

class AccountControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = UserTestUtil.USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(UserTestUtil.jsonMatcher(UserTestUtil.user, UserTestUtil::assertEquals));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestUtil.USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(userRepository.findById(UserTestUtil.USER_ID).isPresent());
        Assertions.assertTrue(userRepository.findById(UserTestUtil.ADMIN_ID).isPresent());
    }

    @Test
    void register() throws Exception {
        User newUser = UserTestUtil.getNew();
        User registered = UserTestUtil.asUser(perform(MockMvcRequestBuilders.post(URL + "/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(writeValue(newUser)))
                .andExpect(status().isCreated()).andReturn());
        int newId = registered.id();
        newUser.setId(newId);
        UserTestUtil.assertEquals(registered, newUser);
        UserTestUtil.assertEquals(registered, userRepository.findById(newId).orElseThrow());
    }

    @Test
    @WithUserDetails(value = UserTestUtil.USER_MAIL)
    void update() throws Exception {
        User updated = UserTestUtil.getUpdated();
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        UserTestUtil.assertEquals(updated, userRepository.findById(UserTestUtil.USER_ID).orElseThrow());
    }
}