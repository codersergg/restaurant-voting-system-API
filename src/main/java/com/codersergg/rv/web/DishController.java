package com.codersergg.rv.web;

import com.codersergg.rv.model.Dish;
import com.codersergg.rv.repository.DishRepository;
import com.codersergg.rv.repository.RestaurantRepository;
import com.codersergg.rv.util.ValidationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

import static com.codersergg.rv.util.ValidationUtil.assureIdConsistent;
import static com.codersergg.rv.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = DishController.URL)
@Transactional
@AllArgsConstructor
@Slf4j
@Tag(name = "Dish Controller")
public class DishController {
    static final String URL = "/api/restaurant";

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/dish/{id}")
    public Dish get(@PathVariable int id) {
        return checkNotFoundWithId(dishRepository.getById(id), id);
    }

    @DeleteMapping("/dish/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        dishRepository.deleteById(id);
    }

    @Transactional
    @PostMapping(value = "/{restaurantId}/dish", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create {} ", dish);
        ValidationUtil.checkNew(dish);
        Dish created = dishRepository.save(dish);
        created.setCreated(LocalDate.now());
        created.setRestaurant(checkNotFoundWithId(restaurantRepository.getById(restaurantId), restaurantId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/dish")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/dish/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Dish update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("update {} ", dish);
        Dish oldDish = checkNotFoundWithId(dishRepository.getById(id), id);
        assureIdConsistent(dish, oldDish.id());
        dish.setId(oldDish.getId());
        dish.setCreated(LocalDate.now());
        dish.setRestaurant(oldDish.getRestaurant());
        if (dish.getName() == null) {
            dish.setName(oldDish.getName());
        }
        if (dish.getPrise() == 0) {
            dish.setPrise(oldDish.getPrise());
        }
        return dishRepository.save(dish);
    }
}
