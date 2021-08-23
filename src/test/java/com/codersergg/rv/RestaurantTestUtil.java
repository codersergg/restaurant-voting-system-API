package com.codersergg.rv;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import com.codersergg.rv.model.Restaurant;
import com.codersergg.rv.model.Vote;
import com.codersergg.rv.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestUtil {

    public static final Restaurant restaurant1 = new Restaurant("A1", 0);
    public static final Restaurant restaurant2 = new Restaurant("B2", 0);
    public static final Restaurant restaurant3 = new Restaurant("Сытый енот", 0);

    public static void assertNoIdEquals(Restaurant actual, Vote expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static Restaurant asRestaurant(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, Restaurant.class);
    }

    public static ResultMatcher jsonMatcher(Restaurant expected, BiConsumer<Restaurant, Restaurant> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asRestaurant(mvcResult), expected);
    }
}
