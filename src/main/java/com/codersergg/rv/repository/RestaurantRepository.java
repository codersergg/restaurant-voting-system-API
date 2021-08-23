package com.codersergg.rv.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.codersergg.rv.model.Restaurant;

@Transactional(readOnly = true)
@Tag(name = "Restaurant Controller")
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Query("SELECT r FROM Restaurant r WHERE r.id = ?1")
    Restaurant getById(Integer integer);

    @Override
    @Modifying
    @Transactional
    @CacheEvict(value = "restaurant", allEntries = true)
    void deleteById(Integer integer);

    @Modifying
    @Transactional
    @CachePut(value = "restaurant")
    Restaurant save(Restaurant restaurant);
}
