package com.codersergg.rv.web;

import com.codersergg.rv.UserTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.codersergg.rv.repository.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codersergg.rv.web.AccountController.URL;

class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(UserTestUtil.jsonMatcher(UserTestUtil.admin, UserTestUtil::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
        Assertions.assertTrue(userRepository.findById(UserTestUtil.USER_ID).isPresent());
        Assertions.assertTrue(userRepository.findById(UserTestUtil.USER_ID+1).isPresent());
        Assertions.assertTrue(userRepository.findById(UserTestUtil.USER_ID+2).isPresent());
        Assertions.assertTrue(userRepository.findById(UserTestUtil.USER_ID+3).isPresent());
        Assertions.assertTrue(userRepository.findById(UserTestUtil.ADMIN_ID).isPresent());
        Assertions.assertFalse(userRepository.findById(UserTestUtil.USER_ID+4).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL))
                .andExpect(status().isNoContent());
        Assertions.assertTrue(userRepository.findById(UserTestUtil.USER_ID).isPresent());
        Assertions.assertFalse(userRepository.findById(UserTestUtil.ADMIN_ID).isPresent());
    }
}