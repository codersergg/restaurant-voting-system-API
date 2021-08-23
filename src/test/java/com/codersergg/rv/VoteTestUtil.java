package com.codersergg.rv;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import com.codersergg.rv.model.Vote;
import com.codersergg.rv.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.BiConsumer;

import static com.codersergg.rv.RestaurantTestUtil.restaurant1;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestUtil {

    private static final LocalDate localDate = LocalDate.now();
    public static final Vote vote = new Vote(localDate, LocalTime.of(10, 55), restaurant1, UserTestUtil.user);

    public static void assertNoIdEquals(Vote actual, Vote expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "restaurant", "user").isEqualTo(expected);
    }

    public static Vote asVote(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, Vote.class);
    }

    public static ResultMatcher jsonMatcher(Vote expected, BiConsumer<Vote, Vote> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asVote(mvcResult), expected);
    }
}
