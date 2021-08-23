package com.codersergg.rv;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import com.codersergg.rv.model.Role;
import com.codersergg.rv.model.User;
import com.codersergg.rv.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestUtil {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final String USER_MAIL = "user1@gmail.com";
    public static final String ADMIN_MAIL = "admin@codersergg.com";
    public static final User user = new User(USER_ID, "user1@gmail.com", "User_First", "User_Last", "password", List.of(Role.USER));
    public static final User admin = new User(ADMIN_ID, ADMIN_MAIL, "Admin_First", "Admin_Last", "admin", List.of(Role.ADMIN, Role.USER));
    public static final User user2 = new User(USER_ID+2, "user2@gmail.com", "User_2", "User_2", "password", List.of(Role.USER));
    public static final User user3 = new User(USER_ID+3, "user3@gmail.com", "User_3", "User_3", "password", List.of(Role.USER));

    public static User getNew() {
        return new User(null, "new@gmail.com", "New_First", "New_Last", "newpass", List.of(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "user_update@gmail.com", "User_First_Update", "User_Last_Update", "password_update", List.of(Role.USER));
    }

    public static void assertEquals(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("password").isEqualTo(expected);
    }

    public static void assertNoIdEquals(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "password").isEqualTo(expected);
    }

    public static User asUser(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, User.class);
    }

    public static ResultMatcher jsonMatcher(User expected, BiConsumer<User, User> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asUser(mvcResult), expected);
    }
}
