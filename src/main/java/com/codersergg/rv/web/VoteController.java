package com.codersergg.rv.web;

import com.codersergg.rv.AuthUser;
import com.codersergg.rv.model.Restaurant;
import com.codersergg.rv.model.Vote;
import com.codersergg.rv.repository.RestaurantRepository;
import com.codersergg.rv.repository.VoteRepository;
import com.codersergg.rv.util.ValidationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.codersergg.rv.util.ValidationUtil.*;

@RestController
@RequestMapping(value = VoteController.URL)
@Transactional
@AllArgsConstructor
@Slf4j
@Tag(name = "Vote Controller")
public class VoteController {
    static final String URL = "/api/account/vote";

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    @GetMapping()
    public Vote getByUserToday(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getByUserToday {} ", authUser);
        return checkNotFound(voteRepository.getByUserToday(authUser.getUser()), "user");
    }

    @Transactional
    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Vote> create(@Valid @RequestBody Vote vote,
                                       @PathVariable int restaurantId,
                                       @AuthenticationPrincipal AuthUser authUser) {
        log.info("create {} {} ", vote, authUser);
        vote.setDate(LocalDate.now());
        vote.setTime(LocalTime.now());
        vote.setUser(authUser.getUser());
        vote.setRestaurant(checkNotFoundWithId(restaurantRepository.getOneById(restaurantId), restaurantId));
        ValidationUtil.checkNew(vote);
        Vote created = voteRepository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL)
                .buildAndExpand(vote.getId()).toUri();
        countRating(restaurantId);
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Transactional
    @PutMapping(value = ("/{restaurantId}"), consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Vote vote,
                       @PathVariable int restaurantId,
                       @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} {}", vote, authUser);
        Vote oldVote = checkNotFound(voteRepository.getByUserToday(authUser.getUser()), "user");
        int oldRestaurantId = oldVote.getRestaurant().id();
        vote.setTime(LocalTime.now());
        checkVoteDeadline(vote);
        vote.setDate(oldVote.getDate());
        ValidationUtil.assureIdConsistent(vote, oldVote.id());
        vote.setId(oldVote.getId());
        vote.setUser(authUser.getUser());
        vote.setRestaurant(checkNotFoundWithId(restaurantRepository.getOneById(restaurantId), restaurantId));
        voteRepository.save(vote);
        countRating(restaurantId, oldRestaurantId);
    }

    public void countRating(int restaurantId) {
        log.info("countRating Restaurant: {} ", restaurantId);
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.getOneById(restaurantId), restaurantId);
        restaurant.setRating(restaurant.getRating() + 1);
        restaurantRepository.save(restaurant);
    }

    public void countRating(int restaurantId, int oldRestaurantId) {
        log.info("countRating Restaurant: {} Restaurant: {} ", restaurantId, oldRestaurantId);
        countRating(restaurantId);
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.getOneById(oldRestaurantId), oldRestaurantId);
        restaurant.setRating(restaurant.getRating() - 1);
        restaurantRepository.save(restaurant);
    }
}
