package com.codersergg.rv.web;

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
import com.codersergg.rv.model.Restaurant;
import com.codersergg.rv.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.codersergg.rv.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = RestaurantController.URL)
@AllArgsConstructor
@Slf4j
@Tag(name = "Restaurant Controller")
public class RestaurantController {
    static final String URL = "/api/restaurant";

    private final RestaurantRepository restaurantRepository;

    @GetMapping()
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return checkNotFoundWithId(restaurantRepository.getById(id), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Restaurant update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} ", restaurant);
        Restaurant oldRestaurant = restaurantRepository.getById(id);
        ValidationUtil.assureIdConsistent(restaurant, oldRestaurant.id());
        restaurant.setId(oldRestaurant.getId());
        restaurant.setRating(oldRestaurant.getRating());
        return restaurantRepository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL)
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
