package com.codersergg.rv.web;

import com.codersergg.rv.model.Dish;
import com.codersergg.rv.repository.DishRepository;
import com.codersergg.rv.to.RestaurantTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.codersergg.rv.util.RestaurantsUtil.createMenuToday;

@RestController
@RequestMapping(value = MenuController.URL)
@AllArgsConstructor
@Slf4j
public class MenuController {
    static final String URL = "/api/account/menu";

    private final DishRepository dishRepository;

    @GetMapping
    public List<RestaurantTo> findAllRestaurantWithDishCreatedToday() {
        log.info("findAllRestaurantWithDishCreatedToday");
        List<Dish> dishList = dishRepository.findAllDishCreatedToday();
        return createMenuToday(dishList);
    }
}
